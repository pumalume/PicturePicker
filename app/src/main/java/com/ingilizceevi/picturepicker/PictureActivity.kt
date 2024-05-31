package com.ingilizceevi.picturepicker

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Chronometer
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import com.ingilizceevi.imageconcept.ImagePanelFragment


class PictureActivity : AppCompatActivity() {
    lateinit var goSignal: FrameLayout
    var numOfImages = 3
    lateinit var imagePanel: ImagePanelFragment
    lateinit var myTimer: Chronometer
    lateinit var myClicks: TextView
    lateinit var cancelButton: ImageView
    lateinit var main: View
    private val imageViewsPanel: MutableList<ImageView> = ArrayList(0)
    private val gameBrain: PanelBrain by viewModels()
    private var gameFinished = false
    lateinit var playButton: ImageView
    private lateinit var soundPlayer: SoundPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_panel_controller)
        var b : Bundle ?= intent.extras
        var c = b!!.getString("chapter")
        c = "chapter"+c

        myTimer = findViewById(R.id.myTimer)
        goSignal = findViewById(R.id.signalCircle)
        myClicks = findViewById(R.id.myClicks)
        playButton = findViewById(R.id.playButton)
        cancelButton = findViewById(R.id.cancelButton)
        soundPlayer = SoundPlayer(this)
        gameBrain.initiateModel(3, c)
        gameBrain.schemaIsInitializeToBeginGame()
        cancelButton.setOnClickListener(onCancelClick)
        playButton.setOnClickListener { playTarget() }
        goSignal.setBackgroundResource(R.drawable.red_circle)
        //soundPlayer = SoundPlayer(this)
        //main.setOnClickListener(initiatorClick)
    }

    val onCancelClick = View.OnClickListener {
        finish()
    }

    fun placeImagesFromActiveGameMap(){
        for(i in 0 until numOfImages){


        }
    }
    private fun startGame() {
        imagePanel = supportFragmentManager.findFragmentById(R.id.laneControllerView) as ImagePanelFragment
        imagePanel.refreshPanelOfImages()
        gameBrain.myTargetConceptIsSetFromIdealMap()
        Handler().postDelayed({
            playTarget()
            myTimer.start()
        }, 500)
    }

    fun playTarget() {

        if (gameBrain.targetConceptAsString() != "") {
            val u = gameBrain.getTargetUri()
            val player = soundPlayer.startSound(u)
            player.setOnCompletionListener {
                setOnClickListeners()
            }
            player.start()
        }
    }

    fun setOnClickListeners() {
        for (i in 0 until numOfImages!!) {
            imageViewsPanel[i].setOnClickListener(myOnClickListener)
        }
        goSignal.setBackgroundResource(R.drawable.green_circle)
    }

    fun nullifyAllImageViewListeners() {
        for (i in 0 until numOfImages) {
            imageViewsPanel[i].setOnClickListener(null)
        }
        goSignal.setBackgroundResource(R.drawable.red_circle)
    }

    fun dealWithTheClicks() {
        gameBrain.increaseClickedCounter()
        val totalClicksSoFar = gameBrain.getTotalClicks().toString()
        myClicks.text = getString(R.string.totalClicks, totalClicksSoFar)

    }

    val myOnClickListener = View.OnClickListener {
        nullifyAllImageViewListeners()
        dealWithTheClicks()
        validateSelection(it.id)
    }

    fun validateSelection(viewId: Int) {
        if (gameBrain.isTargetConceptTrue(viewId)) {
            targetConceptIsTrue(viewId)
        } else targetIsFalse(viewId)
    }

    fun targetConceptIsTrue(viewId: Int) {
        if (!gameBrain.schemaForImageClickedTrue(viewId)) {
            gameFinished = true
        }
        val bigAnim = imagePanel.imageFragmentArray[viewId].enlargerAnimatorIsSetForView()
        bigAnim.doOnEnd { allViewsAreFadedOut(gameFinished) }
        bigAnim.start()
    }

    fun targetIsFalse(viewId: Int) {
        gameBrain.schemaForImageClickedFalse()
        val shakeAnim = imagePanel.imageFragmentArray[viewId].shakerAnimatorIsSetForView()
        shakeAnim.doOnEnd { allViewsAreFadedOut(false) }
        shakeAnim.start()
    }

    fun setupFadeOutAnimator(finished: Boolean): AnimatorSet {
        val fadeOutControl = AnimatorController(imagePanel)
        val fadeOutSet = fadeOutControl.setupFadeOutAnimator()
        fadeOutSet.doOnEnd {
            if (!finished) startAnotherRoundOfFun()
            else concludingFragmentIsLoaded()
        }
        return fadeOutSet
    }

    fun allViewsAreFadedOut(finished: Boolean) {
        setupFadeOutAnimator(finished).start()
    }

    fun concludingFragmentIsLoaded() {
        playButton.visibility = View.INVISIBLE
        goSignal.visibility = View.INVISIBLE
        myTimer.stop()
        val myTime = myTimer.text.toString()
        val myClicks = gameBrain.getTotalClicks().toString()

        //gameBrain.studentInfo.studentClicks = myClicks.toInt()
        //gameBrain.studentInfo.studentTime = gameBrain.calculateStudentTime(myTime)
    }

    fun startAnotherRoundOfFun() {
        imagePanel.refreshPanelOfImages()
        playTarget()
    }
}
