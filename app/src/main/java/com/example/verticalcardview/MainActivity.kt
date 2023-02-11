package com.example.verticalcardview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    lateinit var verticalViewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalViewPager = findViewById(R.id.viewPager2)

        //세로 viewpager 생성
        createVerticalView(getCardViewList())
    }

    private fun createVerticalView(data : ArrayList<Int>){
        verticalViewPager.orientation = ORIENTATION_VERTICAL
        val adapter = VerticalViewPagerAdapter(data)
        verticalViewPager.adapter = adapter

        with(verticalViewPager){
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
        }
        verticalViewPager.setPageTransformer(SwipeTransformer())
    }

    inner class SwipeTransformer : ViewPager2.PageTransformer{

        private val screenHeight = resources.displayMetrics.heightPixels //폰의 높이를 가져옴
        private val pageMarginPy = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        private val offsetPy = resources.getDimensionPixelOffset(R.dimen.offset)
        private val pageHeight = screenHeight - pageMarginPy - offsetPy

        private val MIN_SCALE = 0.75f
        private val MIN_ALPHA = 0.5f
        override fun transformPage(page: View, position: Float) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))

            page.apply {
                if (position < -1) { // [-Infinity,-1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    alpha = 0f
                } else if (position <= 0) { // [-1,0]
                    translationY = pageHeight * -position

                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    alpha =
                        (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                } else if (position <= 1) { // (0,1]
                    alpha = 1f
                    scaleX = 1f
                    scaleY = scaleFactor

                    val viewPager = page.parent.parent as ViewPager2
                    val offset = position * -(2 * offsetPy + pageMarginPy)

                    if (viewPager.orientation == ORIENTATION_VERTICAL) {
                        page.translationY = offset
                    } else {
                        page.translationX = offset
                    }

                } else { // (1,+Infinity]
                    alpha = 0f
                    scaleX = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
                    scaleY = 1f
                }
            }
        }
    }

    //mock-up data
    private fun getCardViewList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example
        )
    }
}