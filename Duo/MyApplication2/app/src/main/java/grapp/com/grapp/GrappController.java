package grapp.com.grapp;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hein on 4/19/2016.
 * Class that executes on application launch.
 * Class sets up all the core Volley components.
 * Class serves as a singleton.
 */
public class GrappController extends Application {
    // TAG string equals the simple version of this classes name.
    public static final String TAG = GrappController.class.getSimpleName();
    // Instance of the class itself.
    private static GrappController instance;
    // Volley request dispatcher.
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    // Singleton, gets the instance of this class.
    public static synchronized GrappController getInstance() {
        return instance;
    }

    // Gets the Volley request dispatcher.
    // If the dispatcher is null, create a new one and return it.
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    // Adds a request to the request dispatcher.
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    // Adds a request to the request dispatcher.
    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    // If the request dispatcher is not null,
    // cancel all requests.
    public void cancelPendingRequest(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
