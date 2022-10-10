package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = " Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likesCounter = 999999,
            sharesCounter = 980
        )
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likeCounter.text = countersCorrector(post.likesCounter)
            shareCounter.text = countersCorrector(post.sharesCounter)

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likesCounter++ else post.likesCounter--
                likeCounter.text = countersCorrector(post.likesCounter)
            }

            shares.setOnClickListener {
                post.sharesCounter++
                shareCounter.text = countersCorrector(post.sharesCounter)
            }
        }
    }
}

private fun countersCorrector(item: Int): String {
    return when (item) {
        in 1000..9999 -> "${roundOffDecimal(item / 1000.0)}K"
        in 10_000..999_999 -> "${(item / 1000)}K"
        in 1_000_000..1_000_000_000 -> "${roundOffDecimal(item / 1_000_000.0)}M"
        else -> "$item"
    }
}

private fun roundOffDecimal(number: Double): String {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toString()
}
