package rs.ac.metropolitan.cs330_dz06_anteaprimorac5157

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import kotlin.random.Random

class MyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moleImageView: ImageView = findViewById(R.id.moleImageView)
        val moleListView: ListView = findViewById(R.id.moleListView)

        // Elementi poprimaju vrijednosti iz strings.xml.
        val moleNames = resources.getStringArray(R.array.mole_names)

        // Podešavanje ArrayAdapter objekta za ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, moleNames)
        moleListView.adapter = adapter

        moleListView.setOnItemClickListener { _, _, position, _ ->
            val drawableId = when (position) {
                0 -> R.drawable.sprout_mole
                1 -> R.drawable.box_mole
                2 -> R.drawable.warden_mole
                else -> {
                    Log.e("MyActivity", "No image available for this position.")
                    0
                }
            }
            if (drawableId != 0) {
                moleImageView.setImageResource(drawableId)
                if (Random.nextInt(10) == 1) { // 10% šanse za trigger intenta
                    Toast.makeText(this, getString(R.string.lucky), Toast.LENGTH_SHORT).show()
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            getString(R.string.email_subject)
                        )
                        putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body))
                    }
                    if (emailIntent.resolveActivity(packageManager) != null) {
                        startActivity(Intent.createChooser(emailIntent, "Send email..."))
                    } else {
                        Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val message = when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> getString(R.string.landscape_toast)
            Configuration.ORIENTATION_PORTRAIT -> getString(R.string.portrait_toast)
            else -> ""
        }

        if (message.isNotEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
