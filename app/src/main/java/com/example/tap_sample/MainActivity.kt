package com.example.tap_sample

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.tap_sample.data.SharedFilesDownload
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.databinding.ActivityMainBinding
import com.example.tap_sample.fragments.FragmentCountDialog
import com.example.tap_sample.fragments.FragmentDiagnostic
import com.example.tap_sample.fragments.FragmentHotList
import com.example.tap_sample.fragments.FragmentNotifications
import com.example.tap_sample.fragments.FragmentScan
import com.example.tap_sample.fragments.FragmentShiftInspection
import com.example.tap_sample.services.NotificationLiveData
import com.example.tap_sample.viewmodels.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.iceteck.silicompressorr.videocompression.MediaController.mContext
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener,
    NavigationView.OnNavigationItemSelectedListener {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private var mFragmentScan: FragmentScan? = null
    private lateinit var toolbar1:Toolbar
    private lateinit var toolbar2:Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var mMenu:Menu
    private lateinit var sharedFilesDownload: SharedFilesDownload
    private var userDetails:Parcelable? = null
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.sharedFilesDownload = SharedFilesDownload()

        openFragmentScan()

        toolbar1  = findViewById(R.id.toolbar1)
        toolbar2  = findViewById(R.id.toolbar2)

        val toolbar = findViewById<Toolbar>(R.id.toolbar1)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.addOnBackStackChangedListener(this)

        drawerLayout = findViewById(R.id.drawer_layout)
         navigationView = findViewById(R.id.navigation)
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mMenu = navigationView.menu

         userDetails = intent.getParcelableExtra<Inspector>("userDetails")!!

        NotificationLiveData.observe(this, { notification ->
            loginViewModel.insertNotification(notification)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                android.R.id.home ->{
                    closeOrOpendrawerLayout()
                    return true

            }
               /* R.id.action_diagnostic ->{
                    clearAllFragments()
                    val fm = supportFragmentManager
                    val transaction: FragmentTransaction = fm.beginTransaction()
                    val fragmentDiagnostic: Fragment = FragmentDiagnostic()
                    transaction.add(R.id.panel_layout, fragmentDiagnostic)
                    transaction.addToBackStack(null)
                    transaction.remove(mFragmentScan!!)
                    transaction.commit()
                }*/

            }
         //   super.onOptionsItemSelected(item)

        return true
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fm = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        closeOrOpendrawerLayout()
        when(item.itemId){
            R.id.action_count -> {
                val fragmentManager = supportFragmentManager
                val countDialog = FragmentCountDialog()
                drawerLayout.closeDrawer(GravityCompat.START)
                countDialog.show(fragmentManager, "counts_dialog")
            }
            R.id.action_camera ->{
                startCamera()

            }
            R.id.action_logout ->{
                logoutUser()

            }
            R.id.action_diagnostic ->{
                clearAllFragments()
                val fragmentDiagnostic: Fragment = FragmentDiagnostic()
                val bundle = Bundle()
                bundle.putParcelable("userDetails", userDetails)
                fragmentDiagnostic.arguments = bundle
                drawerLayout.closeDrawer(GravityCompat.START)
                transaction.add(R.id.panel_layout, fragmentDiagnostic)
                transaction.addToBackStack(null)
                mFragmentScan?.let { it1 -> transaction.remove(it1) }
                transaction.commit()

            }
            R.id.action_inspect ->{
                 clearAllFragments()
                val fragInspect: Fragment = FragmentShiftInspection()
                drawerLayout.closeDrawer(GravityCompat.START)
                transaction.add(R.id.panel_layout, fragInspect)
                transaction.addToBackStack(null)
                mFragmentScan?.let { it1 -> transaction.remove(it1) }
                transaction.commit()

            }
            R.id.action_notification ->{
                 clearAllFragments()
                val fragmentNotifications: Fragment = FragmentNotifications()
                drawerLayout.closeDrawer(GravityCompat.START)
                transaction.add(R.id.panel_layout, fragmentNotifications)
                transaction.addToBackStack(null)
                mFragmentScan?.let { it1 -> transaction.remove(it1) }
                transaction.commit()


            }
            R.id.action_offender -> {
                  clearAllFragments()
                val bun = Bundle()
                val fragOffender: Fragment = FragmentHotList()
                drawerLayout.closeDrawer(GravityCompat.START)
                transaction.add(R.id.panel_layout, fragOffender)
                transaction.addToBackStack(null)
                fragOffender.arguments = bun
                transaction.remove(mFragmentScan!!)
                transaction.commit()
            }

            R.id.action_files ->{
                openFileExplorer()
                if (item.title.toString()
                        .equals(getString(R.string.action_files), ignoreCase = true)
                ) {
                    openFileExplorer()
                } else {
                    Toast.makeText(mContext, item.title, Toast.LENGTH_SHORT).show()
                }

            }

        }
        return true
    }


    private fun openFragmentScan(){
        mFragmentScan = supportFragmentManager
            .findFragmentByTag(getString(R.string.scan)) as FragmentScan?
        if (mFragmentScan == null) {
            mFragmentScan = FragmentScan()
            val bundle = Bundle()
            bundle.putParcelable("userDetails", userDetails)
            mFragmentScan?.arguments = bundle
            val trans = supportFragmentManager
                .beginTransaction()
            trans.add(
                R.id.panel_layout, mFragmentScan!!,
                getString(R.string.scan)
            )
            mFragmentScan!!.setHasOptionsMenu(true)
            trans.commit()

        }

    }


    private fun startCamera() {
        if (!this.packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA
            )
        ) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                .show()
        } else {
            val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(this, getPackageName() + ".provider", f)
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, 0)
        }
    }

    private fun logoutUser() {
        val intent = Intent()
        intent.setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        intent.setClass(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openFileExplorer() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"  // Set the MIME type to filter files
//        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setClassName(
            "com.sec.android.app.myfiles",
            "com.sec.android.app.myfiles.external.ui.MainActivity"
        )
        val uri = Uri.parse(sharedFilesDownload.getSharedFilePath(mContext))
        intent.setDataAndType(uri, "*/*")
        startActivityForResult(Intent.createChooser(intent, "Open folder"), 1)
   }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the selected file URI in the 'data' Intent
            val selectedFileUri = data?.data
            // Process the selected file URI as needed
        }
    }

    companion object {
        private const val PICK_FILE_REQUEST_CODE = 1
    }


    override fun onBackStackChanged() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            setSupportActionBar(toolbar1 as Toolbar)
            toolbar1.visibility = View.VISIBLE
            toolbar2.visibility = View.GONE
        } else {
            setSupportActionBar(toolbar2 as Toolbar)
            toolbar2.visibility = View.VISIBLE
            toolbar1.visibility = View.GONE
        }

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }



    fun setToolbarTitle(title: String?) {
        val mTxtTitle = toolbar2.findViewById<TextView>(R.id.txt_title)
        mTxtTitle.text = title
    }


    fun clearAllFragments() {
        val fm = supportFragmentManager
        val count = fm.backStackEntryCount
        for (i in 0 until count) {
            fm.popBackStack()
        }
    }


   override fun onBackPressed() {
        // TODO Auto-generated method stub
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            if (supportFragmentManager.backStackEntryCount == 0) {
                val exitDialog = MaterialAlertDialogBuilder(this)
                exitDialog.setMessage(getString(R.string.dialog_exit_app))
                exitDialog.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, which -> // cancel trip data logging
                   /* val update: Int = mContext.getContentResolver().update(
                        TapProvider.URI_UPLOAD_TRANSCATION, null,
                        null, null
                    )
                    log.info("User closing app")*/
                    finish()
                }
                exitDialog.setNegativeButton(
                    getString(R.string.buttons_cancel)
                ) { dialog, which -> dialog.dismiss() }
                exitDialog.create()
                exitDialog.show()
            } else {
                super.onBackPressed()
            }
        }
    }


    private fun closeOrOpendrawerLayout() {
        val fm = supportFragmentManager
        val count = fm.backStackEntryCount
        if (count != 0) {
            fm.popBackStack()
        } else {
            if (drawerLayout != null) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT) && mMenu != null) {
                    drawerLayout.openDrawer(Gravity.LEFT)
                    mMenu.findItem(R.id.menu_login_control)
                    mMenu.findItem(R.id.menu_batteryTest)
                    val transactionItem: MenuItem = mMenu.findItem(R.id.menu_upload_trasactions)
                    // transactionItem.setTitle(getString(R.string.action_upload_transactions) + " " + cursor.count)

                    if (navigationView != null) {
                        navigationView.refreshDrawableState()
                    }
                } else {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                }
            }
        }
    }




}