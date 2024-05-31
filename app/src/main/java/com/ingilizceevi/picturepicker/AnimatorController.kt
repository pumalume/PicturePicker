package com.ingilizceevi.picturepicker

import android.animation.AnimatorSet
import androidx.core.animation.doOnEnd
import com.ingilizceevi.imageconcept.ImagePanelFragment

class AnimatorController(ic: ImagePanelFragment) {
    private val imageController : ImagePanelFragment = ic

    fun setupFadeOutAnimator():AnimatorSet{
        val fadeOutSet = AnimatorSet()
        val fadeList = imageController.panelOfFadeOutViews()
        fadeOutSet.playTogether(fadeList)
        return fadeOutSet
    }
}