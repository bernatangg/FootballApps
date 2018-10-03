package com.result.isoftscore.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.nestedScrollView

class TeamDetailOverviewFragment: Fragment(), AnkoComponent<Context> {
    private var teamDescription: String? = null
    private lateinit var teamDescriptionTextView: TextView
    companion object {
        const val ARG_TEAM_DESCRIPTION = "TEAM_DESCRIPTION"
        fun newInstance(teamDescription: String): TeamDetailOverviewFragment {
            val fragment = TeamDetailOverviewFragment()
            val args = Bundle()
            args.putString(ARG_TEAM_DESCRIPTION, teamDescription)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        teamDescription = arguments?.getString(ARG_TEAM_DESCRIPTION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        nestedScrollView {
            lparams(width = matchParent, height = matchParent)
            teamDescriptionTextView = textView {
                setLineSpacing(0f, 1.5f)
            }.lparams { margin = dip(16) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamDescriptionTextView.text = teamDescription
    }
}