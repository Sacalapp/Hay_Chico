package sacalapp.hay_chico;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jarvicfelipe on 27/11/2017.
 */

public class InicioNotificacionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, Notificaciones.class);
            context.startService(pushIntent);
        }
    }
}
