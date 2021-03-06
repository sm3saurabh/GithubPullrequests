package dev.saurabhmishra.githubpullrequests.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.saurabhmishra.githubpullrequests.R
import dev.saurabhmishra.githubpullrequests.databinding.PullRequestItemBinding
import dev.saurabhmishra.githubpullrequests.extensions.formatPullRequestDate
import dev.saurabhmishra.githubpullrequests.models.PullRequest

class PullRequestsAdapter :
    PagingDataAdapter<PullRequest, PullRequestsAdapter.PullRequestViewHolder>(PullRequestsAdapter) {

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.pull_request_item,
            parent,
            false
        ))
    }

    class PullRequestViewHolder(
        private val binding: PullRequestItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pullRequest: PullRequest?) {
            pullRequest?.also {
                binding.title.text = pullRequest.title
                binding.closedAt.text = pullRequest.closedAt?.formatPullRequestDate().orEmpty()
                binding.createdAt.text = pullRequest.createdAt?.formatPullRequestDate().orEmpty()
                binding.userName.text = pullRequest.user?.login.orEmpty()
                binding.profileImage.load(pullRequest.user?.avatarUrl)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<PullRequest>() {
        override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem == newItem
        }

    }
}