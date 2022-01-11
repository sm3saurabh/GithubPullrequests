package dev.saurabhmishra.githubpullrequests.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.saurabhmishra.githubpullrequests.R
import dev.saurabhmishra.githubpullrequests.base.BaseActivity
import dev.saurabhmishra.githubpullrequests.databinding.ActivityHomeBinding
import dev.saurabhmishra.githubpullrequests.extensions.safeLaunch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    private val pullRequestsAdapter = PullRequestsAdapter()

    override fun layoutId(): Int {
        return R.layout.activity_home
    }

    override fun viewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPagerAndAdapter()
        setupRecycler()

    }

    private fun setupPagerAndAdapter() {
        lifecycleScope.safeLaunch {
            viewModel.pagedPullRequests.collectLatest(pullRequestsAdapter::submitData)
        }
    }

    private fun setupRecycler() {
        pullRequestsAdapter.withLoadStateFooter(
            footer = PullRequestLoadingFooter {
                pullRequestsAdapter.retry()
            }
        )
    }

    companion object {
        fun launch(currentActivity: Activity) {
            val launchingIntent = Intent(currentActivity, HomeActivity::class.java)
            currentActivity.startActivity(launchingIntent)
            currentActivity.finish()
        }
    }

}