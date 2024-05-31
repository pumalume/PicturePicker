package com.ingilizceevi.imageconcept

import android.animation.Animator
import android.animation.AnimatorInflater
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.ingilizceevi.mylibrary.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ImageFragment : Fragment() {

    private var imageTag: Int? = null
    private lateinit var mainView: View
    private lateinit var imageView: ImageView
    private lateinit var imageFrame: FrameLayout
    //private val gameBrain: LaneViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageTag = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_image_frame, container, false)
        imageView = mainView.findViewById(R.id.imageView)
        imageFrame = mainView.findViewById(R.id.imageFrame)
        if (imageTag != null) { imageView.id = imageTag!! }
        return mainView
    }

    fun setImageWith(drawable: Drawable){
        imageView.setImageDrawable(drawable)
    }
    fun handleOnImageFrame(): FrameLayout {
        return imageFrame
    }

    fun handleOnImageView(): ImageView {
        return imageView
    }


    fun imageValuesAreReset() {
        imageView.alpha = 1f
        imageView.rotation = 0F
        imageView.scaleX = 1.0f
        imageView.scaleY = 1.0f

    }

    fun getCurrentCoordinatesOnScreen(): IntArray {
        val frame = mainView.findViewById<FrameLayout>(R.id.imageFrame)
        val location = IntArray(2)
        frame.getLocationInWindow(location)
        return location
    }

    fun alphaAnimatorReturnedToFull() {
        if (imageView.alpha != 1f) {
            AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_animator)
                .apply {
                    setTarget(imageView)
                    start()
                }
        }
    }

    fun enlargerAnimatorIsSetForView(): Animator {
        val myAnimator =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.enlarger_animator)
        myAnimator.setTarget(imageView)
        return myAnimator
    }

    fun shakerAnimatorIsSetForView(): Animator {
        val myAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.shaker_animator)
        myAnimator.setTarget(imageView)
        return myAnimator
    }

    fun fadeAnimatorIsSetForView(): Animator {
        val myFader = AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_animator)
        myFader.setTarget(imageView)
        return myFader
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}