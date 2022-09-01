package video.api.integration.services;

import android.app.Notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import video.api.client.api.models.Video;
import video.api.client.api.services.UploadService;

public class UploadServiceWithoutNotifications extends UploadService {
    @Nullable
    @Override
    public Notification onUploadStartedNotification(@NonNull String id) {
        return null;
    }

    @Nullable
    @Override
    public Notification onUploadSuccessNotification(@NonNull String id, @NonNull Video video) {
        return null;
    }

    @Nullable
    @Override
    public Notification onUploadProgressNotification(@NonNull String id, int progress) {
        return null;
    }

    @Nullable
    @Override
    public Notification onUploadErrorNotification(@NonNull String id, @NonNull Exception e) {
        return null;
    }

    @Nullable
    @Override
    public Notification onUploadCancelledNotification(@NonNull String id) {
        return null;
    }

    @Nullable
    @Override
    public Notification onLastUploadNotification() {
        return null;
    }
}
