package android.epita.fr.gitandroidapi.sessionsfunctions;

import android.content.Context;
import android.net.ConnectivityManager;

public class Functions {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
