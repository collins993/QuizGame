package io.github.collins993.quizgame.Controller

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import io.github.collins993.quizgame.Model.DownloadingObject
import io.github.collins993.quizgame.Model.Plant
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.collins993.quizgame.Model.ParsePlantUtility
import io.github.collins993.quizgame.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    /*
    private var cameraButton: Button? = null
    private var galleryButton: Button? = null
    private var imageView: ImageView? = null

    val OPEN_CAMERA_BTN_ID = 1000;
    val OPEN_GALLERY_BTN_ID = 2000;

     */


    var correctAnswerIndex: Int = 0
    var correctPlant: Plant? = null
    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var numberOfTimesUserAnsweredInCorrectly: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        setProgressBar(false)

        displayUIWidgets(false)

        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(5)
                .playOn(btn_next_plant)

        //see the next plant
        btn_next_plant.setOnClickListener(View.OnClickListener {

            if (checkForInternetConnection()) {

                setProgressBar(true)

                try {

                    val innerClassObject = DownloadingPlantTask()

                    innerClassObject.execute()

                }catch (e: Exception) {

                    e.printStackTrace()
                }

//                 findViewById<Button>(R.id.button_1).setBackgroundColor(Color.BLUE)
//                 findViewById<Button>(R.id.button_2).setBackgroundColor(Color.BLUE)
//                 findViewById<Button>(R.id.button_3).setBackgroundColor(Color.BLUE)
//                 findViewById<Button>(R.id.button_4).setBackgroundColor(Color.BLUE)

                var gradientColor: IntArray = IntArray(2)

                    gradientColor.set(0, Color.parseColor("#FD191917"))

                    gradientColor.set(1, Color.parseColor("#500606"))

                var gradientDrawable: GradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientColor)

                var convertDipValue = dipToFloat(this@MainActivity, 50f)

                gradientDrawable.setCornerRadius(convertDipValue)

                gradientDrawable.setStroke(5, Color.parseColor("#ffffff"))

                 button_1.setBackground(gradientDrawable)
                 button_2.setBackground(gradientDrawable)
                 button_3.setBackground(gradientDrawable)
                 button_4.setBackground(gradientDrawable)

            }
        })

        /*

       cameraButton = findViewById<Button>(R.id.btn_open_camera)

       galleryButton = findViewById<Button>(R.id.btn_open_gallery)

       imageView = findViewById<ImageView>(R.id.imageView2)

       cameraButton?.setOnClickListener(View.OnClickListener {

           Toast.makeText(this, "The Camera Button Clicked..", Toast.LENGTH_SHORT).show()

           val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

           startActivityForResult(cameraIntent, OPEN_CAMERA_BTN_ID)


       })

       galleryButton?.setOnClickListener(View.OnClickListener {

           val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

           startActivityForResult(galleryIntent, OPEN_GALLERY_BTN_ID)
       })

        */

        /*
        Toast.makeText(this, "The Oncreate Method is Called..", Toast.LENGTH_SHORT).show()

        val myPlant: Plant = Plant("", "", "", "", "", "", 0, 0)

         */


    }

    fun dipToFloat(context: Context, dipValue: Float): Float {

        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }

    override fun onResume() {
        super.onResume()

        checkForInternetConnection()
    }

        /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OPEN_CAMERA_BTN_ID) {

            if (resultCode == RESULT_OK) {

                val imageData = data?.getExtras()?.get("data") as Bitmap

                imageView?.setImageBitmap(imageData)
            }
        }

        if (requestCode == OPEN_GALLERY_BTN_ID) {

            if (resultCode == RESULT_OK) {

                val contentURI = data?.getData()

                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                imageView?.setImageBitmap(bitmap)
            }
        }
    }

        */

//    Async Task for Background Threading
    inner class DownloadingPlantTask: AsyncTask<String, Int, List<Plant>>() {

        override fun doInBackground(vararg params: String?): List<Plant>? {
            /**
            val downloadingObject: DownloadingObject = DownloadingObject()

            var jsonData = downloadingObject.downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")

            Log.i("JSON", jsonData) **/

            val parsePlant = ParsePlantUtility()

            return parsePlant.parsePlantObjectFromJSONData();
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            var numberOfPlants = result?.size ?: 0

            if (numberOfPlants > 0) {

                var randomPlantIndexForButton1: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton2: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton3: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton4: Int = (Math.random() * result!!.size).toInt()

                var allRandomPlants = ArrayList<Plant>()

                allRandomPlants.add(result.get(randomPlantIndexForButton1))
                allRandomPlants.add(result.get(randomPlantIndexForButton2))
                allRandomPlants.add(result.get(randomPlantIndexForButton3))
                allRandomPlants.add(result.get(randomPlantIndexForButton4))

                button_1.text = result.get(randomPlantIndexForButton1).toString()
                button_2.text = result.get(randomPlantIndexForButton2).toString()
                button_3.text = result.get(randomPlantIndexForButton3).toString()
                button_4.text = result.get(randomPlantIndexForButton4).toString()

                correctAnswerIndex = (Math.random() * allRandomPlants.size).toInt()

                correctPlant = allRandomPlants.get(correctAnswerIndex)

                val downloadingImageTask = DownloadingImageTsk()

                downloadingImageTask.execute(allRandomPlants.get(correctAnswerIndex).picture_name)
            }
        }


    }

    //downloading image process
    inner class DownloadingImageTsk: AsyncTask<String, Int, Bitmap?>() {
        override fun doInBackground(vararg pictureName: String?): Bitmap? {

            try {

                val downloadingObject = DownloadingObject()

                val plantBitmap: Bitmap? = downloadingObject.downloadPlantPicture(pictureName[0])

                return plantBitmap

            }catch (e: Exception) {

                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            setProgressBar(false)

            displayUIWidgets(true)

            playAnimationOnView(findViewById<ImageView>(R.id.imageView2), Techniques.Tada)
            playAnimationOnView(findViewById<Button>(R.id.button_1), Techniques.RollIn)
            playAnimationOnView(findViewById<Button>(R.id.button_2), Techniques.RollIn)
            playAnimationOnView(findViewById<Button>(R.id.button_3), Techniques.RollIn)
            playAnimationOnView(findViewById<Button>(R.id.button_4), Techniques.RollIn)
            playAnimationOnView(findViewById<TextView>(R.id.textView), Techniques.Swing)
            playAnimationOnView(findViewById<TextView>(R.id.txt_wrong_ans), Techniques.FlipInX)
            playAnimationOnView(findViewById<TextView>(R.id.txt_right_ans), Techniques.Landing)

            findViewById<ImageView>(R.id.imageView2).setImageBitmap(result)

        }


    }

//    button click functions
    fun btn1Click(view:View) {

        specifyTheRightAndWrongAnswer(0)

    }

    fun btn2Click(view:View) {

       specifyTheRightAndWrongAnswer(1)
    }

    fun btn3Click(view:View) {

        specifyTheRightAndWrongAnswer(2)
    }

    fun btn4Click(view:View) {

        specifyTheRightAndWrongAnswer(3)
    }

    //check for interent connection
    private fun checkForInternetConnection(): Boolean {

        val  connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        val isDeviceConnectedToInternet = networkInfo != null && networkInfo.isConnectedOrConnecting

        if (isDeviceConnectedToInternet) {

            return true
        }
        else{

            createAlert()

            return false
        }
    }

    private fun createAlert() {

        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()

        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please Check for Internet Connection")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
             dialog: DialogInterface?, which: Int ->

             startActivity(Intent(Settings.ACTION_SETTINGS))

        })

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", {
                dialog: DialogInterface?, which: Int ->

                Toast.makeText(this, "You must be Connected to the internet", Toast.LENGTH_SHORT).show()

                finish()
        })

        alertDialog.show()
    }

    //specify the right or wrong answer
    private fun specifyTheRightAndWrongAnswer(userGuess: Int) {

        var gradientColor: IntArray = IntArray(2)

        gradientColor.set(0, Color.parseColor("#06AE0D"))

        gradientColor.set(1, Color.parseColor("#06AE0D"))

        var gradientDrawable: GradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientColor)

        var convertDipValue = dipToFloat(this@MainActivity, 50f)

        gradientDrawable.setCornerRadius(convertDipValue)

        gradientDrawable.setStroke(5, Color.parseColor("#ffffff"))

        when(correctAnswerIndex) {

                0 ->    findViewById<Button>(R.id.button_1).setBackground(gradientDrawable)
                1->    findViewById<Button>(R.id.button_2).setBackground(gradientDrawable)
                2->    findViewById<Button>(R.id.button_3).setBackground(gradientDrawable)
                3->    findViewById<Button>(R.id.button_4).setBackground(gradientDrawable)

        }

        if (userGuess == correctAnswerIndex) {

            findViewById<TextView>(R.id.textView).setText("Right!")

            numberOfTimesUserAnsweredCorrectly++

            findViewById<TextView>(R.id.txt_right_ans).setText("$numberOfTimesUserAnsweredCorrectly")
        }
        else{

            var correctPlantName = correctPlant.toString()

            findViewById<TextView>(R.id.textView).setText("Wrong! : $correctPlantName")

            numberOfTimesUserAnsweredInCorrectly++

            findViewById<TextView>(R.id.txt_wrong_ans).setText("$numberOfTimesUserAnsweredInCorrectly")
        }
    }

    //PROGRESS BAR visibility
    private fun setProgressBar(show: Boolean) {

        if (show) {

            findViewById<LinearLayout>(R.id.pLinearLayout).setVisibility(View.VISIBLE)

            findViewById<ProgressBar>(R.id.progressBar).setVisibility(View.VISIBLE)

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else if (!show) {

            findViewById<LinearLayout>(R.id.pLinearLayout).setVisibility(View.GONE)

            findViewById<ProgressBar>(R.id.progressBar).setVisibility(View.GONE)

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    //set visibility of UI image
    private fun displayUIWidgets(display: Boolean) {

        if (display) {

            findViewById<ImageView>(R.id.imageView2).setVisibility(View.VISIBLE)
            findViewById<Button>(R.id.button_1).setVisibility(View.VISIBLE)
            findViewById<Button>(R.id.button_2).setVisibility(View.VISIBLE)
            findViewById<Button>(R.id.button_3).setVisibility(View.VISIBLE)
            findViewById<Button>(R.id.button_4).setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.textView).setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.txt_wrong_ans).setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.txt_right_ans).setVisibility(View.VISIBLE)

        }
        else if (!display){

            findViewById<ImageView>(R.id.imageView2).setVisibility(View.INVISIBLE)
            findViewById<Button>(R.id.button_1).setVisibility(View.INVISIBLE)
            findViewById<Button>(R.id.button_2).setVisibility(View.INVISIBLE)
            findViewById<Button>(R.id.button_3).setVisibility(View.INVISIBLE)
            findViewById<Button>(R.id.button_4).setVisibility(View.INVISIBLE)
            findViewById<TextView>(R.id.textView).setVisibility(View.INVISIBLE)
            findViewById<TextView>(R.id.txt_wrong_ans).setVisibility(View.INVISIBLE)
            findViewById<TextView>(R.id.txt_right_ans).setVisibility(View.INVISIBLE)

        }

    }

    //playing animation
    private fun playAnimationOnView(view: View?, techniques: Techniques) {

        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(view)
    }

}