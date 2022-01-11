package dev.saurabhmishra.githubpullrequests.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.saurabhmishra.githubpullrequests.R
import dev.saurabhmishra.githubpullrequests.databinding.PullRequestLoadStateFooterViewBinding

class PullRequestLoadingFooter(
    private val retry: () -> Unit
) : LoadStateAdapter<PullRequestLoadingFooter.PullRequestLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PullRequestLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PullRequestLoadStateViewHolder {
        return PullRequestLoadStateViewHolder.create(parent, retry)
    }

    class PullRequestLoadStateViewHolder private constructor(
        private val binding: PullRequestLoadStateFooterViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.also {
                it.setOnClickListener { retry.invoke() }
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): PullRequestLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pull_request_load_state_footer_view, parent, false)
                val binding = PullRequestLoadStateFooterViewBinding.bind(view)
                return PullRequestLoadStateViewHolder(binding, retry)
            }
        }
    }
}