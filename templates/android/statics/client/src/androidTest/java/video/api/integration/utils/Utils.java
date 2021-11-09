package video.api.integration.utils;

import android.os.FileUtils;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    /**
     * Copy asset to internal cache directory
     */
    public static File getFileFromAsset(String fileName) throws IOException {
        InputStream is = InstrumentationRegistry.getInstrumentation().getContext().getResources().getAssets().open(fileName);
        File file = new File(InstrumentationRegistry.getInstrumentation().getContext().getCacheDir(), fileName);
        FileOutputStream os = new FileOutputStream(file);
        FileUtils.copy(is, os);

        return file;
    }
}
