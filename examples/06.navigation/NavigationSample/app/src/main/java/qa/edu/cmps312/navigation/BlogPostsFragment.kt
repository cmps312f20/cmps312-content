package qa.edu.cmps312.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_blog_posts.view.*

class BlogPostsFragment : Fragment(R.layout.fragment_blog_posts) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.addBlogPostFab.setOnClickListener {
            findNavController().navigate(R.id.toAddBlogPost)
        }
        view.firstTv.setOnClickListener {
            findNavController().navigate(R.id.toBlogPostDetail)
            // Or
            //findNavController().navigate(R.id.secondFragment)
        }
    }
}