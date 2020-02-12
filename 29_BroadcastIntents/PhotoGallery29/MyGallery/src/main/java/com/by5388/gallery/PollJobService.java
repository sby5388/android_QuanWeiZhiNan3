package com.by5388.gallery;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.by5388.gallery.PollService.PERM_PRIVATE;
import static com.by5388.gallery.PollService.SHOW_NOTIFICATION;

/**
 * @author Administrator  on 2019/12/13.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {
    private static final int POLL_JOB_ID = 456789;
    private static final String TAG = "PollJobService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15);

    public static Intent newIntent(Context context) {
        return new Intent(context, PollJobService.class);
    }

    private PollTask mPollTask;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    /**
     * TODO 该方法返回false结果表示：“交代的任务我已全力去做，现在做完了。”
     * TODO 返回true结果则表示：“任务收到，正在做，但是还没有做完。”
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        if (!PollService.isNetworkAvailableAndConnected(this)) {
            return false;
        }
        mPollTask = new PollTask(this);
        mPollTask.execute(params);
        return true;
    }

    /**
     * TODO 任务运行时也可能会收到onStopJob(JobParameters)调用，表明当前任务需中断。例如，
     * TODO  用户通常需要服务在有WiFi连接时才运行。如果任务还在处理，手机就离开了WiFi覆盖区，
     * TODO  onStopJob(...)方法就会被调用，也就是说，一切任务应立即停止。
     * <p>
     * TODO 调用onStopJob(...)方法就是表明，服务马上就要停掉了。不要抱有幻想，请立即停止手头上的一切事情。
     * TODO 这里，返回true表示：“任务应该计划在下次继续。”
     * TODO 返回false表示：“不管怎样，事情到此结束吧，不要计划下次了。”
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        if (mPollTask != null) {
            //TODO 取消AsyncTask,true:也许会中断正在运行的任务
            mPollTask.cancel(true);
        }
        return false;
    }

    private void showNotification() {
        PollService.showBackgroundNotification(this,0);

    }

    /**
     * 开启或者关闭闹钟服务
     *
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(@NonNull Context context, boolean isOn) {
        final JobScheduler jobScheduler = Objects.requireNonNull((JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE));

        if (isOn) {
            if (!isServiceAlarmOn(context)) {
                final JobInfo jobInfo = new JobInfo.Builder(POLL_JOB_ID, new ComponentName(context, PollJobService.class))
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPeriodic(POLL_INTERVAL_MS)
                        .setPersisted(true)
                        .build();
                jobScheduler.schedule(jobInfo);
            }
        } else {
            if (isServiceAlarmOn(context)) {
                jobScheduler.cancel(POLL_JOB_ID);
            }

        }
    }


    /**
     * 定时器任务是否已经开启
     *
     * @return
     */
    public static boolean isServiceAlarmOn(Context context) {
        final JobScheduler jobScheduler = Objects.requireNonNull((JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE));
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            //JobScheduler检查是否已计划好了任务（已添加到任务中）
            if (jobInfo.getId() == POLL_JOB_ID) {
                return true;
            }
        }
        return false;
    }


    private static class PollTask extends AsyncTask<JobParameters, Void, Boolean> {
        private WeakReference<PollJobService> mReference;

        PollTask(PollJobService pollJobService) {
            mReference = new WeakReference<>(pollJobService);
        }

        @Override
        protected Boolean doInBackground(JobParameters... jobParameters) {
            final PollJobService pollJobService = mReference.get();
            final JobParameters jobParameter = jobParameters[0];

            final String storedQuery = QueryPreferences.getStoredQuery();
            final String lastResultId = QueryPreferences.getLastResultId();
            List<GalleryItem> itemList;
            if (TextUtils.isEmpty(storedQuery)) {
                itemList = new FlickrFetchr().fetchItems(1);
            } else {
                itemList = new FlickrFetchr().searchPhotos(storedQuery);
            }
            if (itemList == null || itemList.size() == 0) {
                pollJobService.jobFinished(jobParameter, true);
                return false;
            }
            final String resultId = itemList.get(0).getId();
            final boolean update = lastResultId.equals(resultId);
            if (update) {
                Log.i(TAG, "Got an old result: " + resultId);
            } else {
                Log.i(TAG, "Got an old result: " + resultId);
            }
            QueryPreferences.setLastResultId(resultId);
            // TODO: 2019/12/31 dump action
            //TODO 任务完成后，就可以调用jobFinished(JobParameters, boolean)方法通知结果。不过，
            //TODO 如果该方法的第二个参数传入true的话，就等于说：“事情这次做不完了，请计划在下次某个时间继续吧。”
            pollJobService.jobFinished(jobParameter, false);
            return update;
        }

        @Override
        protected void onPostExecute(Boolean update) {
            super.onPostExecute(update);
            if (!update) {
                return;
            }
            final PollJobService pollJobService = mReference.get();
            if (pollJobService != null) {
                pollJobService.showNotification();
            }

        }
    }
}
