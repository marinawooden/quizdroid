import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import edu.uw.ischool.mwoode.quizdroid.AndroidDownloader


class MyBackgroundThread(private val applicationContext: Context) : Thread() {

    private val handler = Handler(Looper.getMainLooper())
    private var isThreadRunning = false
    private lateinit var url: String

    override fun run() {
        Log.i("INFO", "RUN THREAD!")
        url = applicationContext.getSharedPreferences("quizdroid",Context.MODE_PRIVATE).getString("Q_URL", "")
            .toString();

        isThreadRunning = true
        while (isThreadRunning) {

            val downloader = AndroidDownloader(applicationContext)
            downloader.downloadFile(url)

            // Display a Toast every 5 seconds
            handler.post {
                Toast.makeText(
                    applicationContext,
                    "http://tednewardsandbox.site44.com/questions.json",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Log.i("INFO", "RUNNING!")

            try {
                // Sleep for 30 seconds
                sleep(30000)
            } catch (e: InterruptedException) {
                // Handle interruption if needed
                e.printStackTrace()
            }
        }
    }


    fun stopThread() {
        Log.i("INFO", "Stopping")
        isThreadRunning = false
        interrupt()
    }
}
