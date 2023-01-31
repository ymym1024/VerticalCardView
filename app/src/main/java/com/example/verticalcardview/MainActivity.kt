package com.example.verticalcardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import kotlin.math.abs
import kotlin.math.max

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
        verticalViewPager.adapter = VerticalViewPagerAdapter(data)

        with(verticalViewPager){
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
        }

        // 애니메이션 추가
        val MIN_SCALE = 0.85f
        val MIN_ALPHA = 0.5f
        val pageMarginPy = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPy = resources.getDimensionPixelOffset(R.dimen.offset)

        verticalViewPager.setPageTransformer { page, position ->
            page.apply {
               when{
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <=1 ->{
                        val scaleFactor = max(MIN_SCALE, 1 - abs(position))

                        val viewPager = page.parent.parent as ViewPager2
                        val offset = position * -(2 * offsetPy+pageMarginPy)
                        if(viewPager.orientation == ORIENTATION_VERTICAL){
                            page.translationY = offset
                        }else{
                            page.translationX = offset
                        }

                        scaleX = 1f
                        scaleY = scaleFactor
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> {
                        alpha = 0f
                    }
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