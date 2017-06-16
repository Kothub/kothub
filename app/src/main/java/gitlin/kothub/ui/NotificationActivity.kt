package gitlin.kothub.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import gitlin.kothub.R
import gitlin.kothub.adapters.NotificationAdapter
import gitlin.kothub.github.api.UserService
import gitlin.kothub.github.api.data.Notifications
import gitlin.kothub.github.api.getService
import kotlinx.android.synthetic.main.activity_notifs.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger

class NotificationActivity : LifecycleAppCompatActivity(),
                             AnkoLogger, SwipeRefreshLayout.OnRefreshListener {

    var notifications: Notifications? = null
        set(value) {
            if (value != null) {
                val layoutManager = LinearLayoutManager(this)

                notifs.adapter = NotificationAdapter(this, value.notifications)
                notifs.layoutManager = layoutManager

                val dividerItemDecoration = DividerItemDecoration(notifs.context, layoutManager.orientation)
                notifs.addItemDecoration(dividerItemDecoration)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifs)
        setSupportActionBar(toolbar)
        initDrawer(toolbar)

        fetchNotifications()

        refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        fetchNotifications()
    }


    override fun onResume() {
        super.onResume()
        drawer.select(drawer.notifs)
    }

    private fun success(notifications: Notifications) {
        this.notifications = notifications
        refreshLayout.isRefreshing = false
    }

    fun fetchNotifications() {
        getService<UserService>()
                .notifications()
                .subscribe(this::success, {})
    }
}