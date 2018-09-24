package nl.giorgos.appie;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.test.runner.AndroidJUnitRunner;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;

/**
 * Test runner which disables the keyguard and wake up the screen during execution. Requires test
 * application have {@code android.permission.DISABLE_KEYGUARD} permission declared.
 **/
public class AppieTestRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final Application app = (Application) getTargetContext().getApplicationContext();
                final String simpleName = AppieTestRunner.class.getSimpleName();

                // Unlock the device so that the tests can input keystrokes.
                ((KeyguardManager) app.getSystemService(KEYGUARD_SERVICE))
                    .newKeyguardLock(simpleName).disableKeyguard();

                // Wake up the screen.
                ((PowerManager) app.getSystemService(POWER_SERVICE))
                    .newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, simpleName)
                    .acquire();
            }
        });
        super.onStart();
    }

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockApp.class.getName(), context);
    }
}
