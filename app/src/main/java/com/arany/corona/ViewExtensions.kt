package com.arany.corona

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun AppCompatActivity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        statusBarColor = Color.TRANSPARENT
    }
    supportActionBar?.hide()
}

fun EditText.onTextChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //Not needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //Not needed
        }
    })
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.setVisibility(isVisible: Boolean){
    if(isVisible) show()
    else hide()
}

fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun TabLayout.setMargin(left: Int, right: Int, top: Int, bottom: Int){
    for (position in 0 until tabCount) {
        val tab: View =  (getChildAt(0) as ViewGroup).getChildAt(position)
        val p: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        tab.requestLayout()
    }
}

fun ShimmerFrameLayout.startShimming() {
    startShimmer()
    show()
}

fun ShimmerFrameLayout.stopShimming() {
    stopShimmer()
    hide()
}

fun ImageView.changeTint(colorResourceId: Int) {
    ImageViewCompat.setImageTintList(
        this, ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                colorResourceId
            )
        )
    )
}

fun TextView.changeTextColor(colorId: Int){
    setTextColor(ContextCompat.getColor(this.context, colorId))
}

fun <T> View.setMargins(left: Int, top: Int, right: Int, bottom: Int){
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(left, top, right, bottom)
    }
}

fun <T> View.setMarginTop(top: Int){
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, top, params.rightMargin, params.bottomMargin)
    }
}

fun <T> View.setMarginBottom(bottom: Int){
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottom)
    }
}

fun View.show(s: String) {
    var temp = "Please fill all fields"
    if(!s.equals("")) temp = s
    val snackbar = Snackbar
        .make(this,temp , Snackbar.LENGTH_LONG)
        .setAction("Ok", View.OnClickListener {

        })
    snackbar.show()
}

fun View.getView(context: Context): ViewGroup.LayoutParams{
    val param = layoutParams as Toolbar.LayoutParams
    param.setMargins(0,0,context.dpTpPix(10F),0)
    layoutParams = param
    return layoutParams
}

fun Context.View(params: ViewGroup.LayoutParams): View {
    val view = ImageView(this)
    //TODO CHECK BELOW COMMENTED CODE
    //view.setImageResource(R.drawable.back_arrow_white)
    view.setPadding(dpTpPix(10F),0,dpTpPix(10F),0)
    view.layoutParams = params
    return view
}

fun Context.dpTpPix(dip:Float):Int{
    val r: Resources = resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        r.displayMetrics
    ).toInt()
}

fun Activity.setActionBarTitle(view: Int, text: String){
    this as AppCompatActivity
    supportActionBar?.customView?.findViewById<TextView>(view)?.text = text
}

fun Activity.setActionBarNavigation(view: Int, color: Int = Color.WHITE){
    this as AppCompatActivity
    setStatusBarColor(color)
    supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_HOME
    supportActionBar?.setHomeAsUpIndicator(view)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    supportActionBar?.show()
}

fun Activity.setActionBarWithLogo(logo: Int, color: Int = Color.WHITE){
    this as AppCompatActivity
    setStatusBarColor(color)
    supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_HOME
    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    supportActionBar?.setDisplayUseLogoEnabled(true)
    supportActionBar?.setLogo(logo)
    supportActionBar?.show()
}

fun Activity.setStatusBarColor(color: Int = Color.WHITE){
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}

fun MaterialButton.onClicked() = callbackFlow<Unit> {
    setOnClickListener { offer(Unit) }
    awaitClose { setOnClickListener(null) }
}

/*fun RangeSlider.setRange(priceRange: Price, from: Float, to: Float) {
    valueFrom = Float.MIN_VALUE
    valueTo = Float.MAX_VALUE
    setValues(from, to)
    valueFrom = priceRange.minPrice.toFloat()
    valueTo = priceRange.maxPrice.toFloat()
}*/

fun Activity.getView(view: Int):View?{
    this as AppCompatActivity
    return supportActionBar?.customView?.findViewById(view)
}

fun Fragment.setDialogueFragment(fragment: BottomSheetDialogFragment, tag: String){
    val transaction = childFragmentManager.beginTransaction()
    val previous = childFragmentManager.findFragmentByTag(tag)
    if (previous != null) {
        transaction.remove(previous)
    }
    transaction.addToBackStack(null)
    fragment.show(transaction, tag)
}

fun Fragment.setDialogueFragment(fragment: DialogFragment, tag: String){
    val transaction = childFragmentManager.beginTransaction()
    val previous = childFragmentManager.findFragmentByTag(tag)
    if (previous != null) {
        transaction.remove(previous)
    }
    transaction.addToBackStack(null)
    fragment.show(transaction, tag)
}

fun AppCompatActivity.setDialogueFragment(fragment: BottomSheetDialogFragment, tag: String){
    val transaction = supportFragmentManager.beginTransaction()
    val previous = supportFragmentManager.findFragmentByTag(tag)
    if (previous != null) {
        transaction.remove(previous)
    }
    transaction.addToBackStack(null)
    fragment.show(transaction, tag)
}

fun View.setHeightAsZero() {
    val lp: ViewGroup.LayoutParams = this.layoutParams as ViewGroup.LayoutParams
    lp.height = 0
}

fun View.setHeightAsWrapContent() {
    val lp: ViewGroup.LayoutParams = this.layoutParams as ViewGroup.LayoutParams
    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
}

fun View.setWidthAsZero() {
    val lp: ViewGroup.LayoutParams = this.layoutParams as ViewGroup.LayoutParams
    lp.width = 0
}

fun View.setWidthAsWrapContent() {
    val lp: ViewGroup.LayoutParams = this.layoutParams as ViewGroup.LayoutParams
    lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
}

fun TabLayout.changeTabFont(fontFamily: Int, textSize: Float) {
    val vg = getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (j in 0 until tabsCount) {
        val vgTab = vg.getChildAt(j) as ViewGroup
        val tabChildsCount = vgTab.childCount
        for (i in 0 until tabChildsCount) {
            val tabViewChild = vgTab.getChildAt(i)
            if (tabViewChild is TextView) {
                tabViewChild.typeface = ResourcesCompat.getFont(this.context, fontFamily)
                tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize)
            }
        }
    }
}

fun AppCompatCheckBox.changeStatusWithoutListener(status: Boolean, listener: CompoundButton.OnCheckedChangeListener){
    setOnCheckedChangeListener(null)
    isChecked = status
    setOnCheckedChangeListener(listener)
}

fun RecyclerView.removeAllItemDecoration(){
    while (itemDecorationCount > 0) removeItemDecorationAt(0)
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.toast(info: String) { requireContext().toast(info) }

fun Activity.toast(info: String) { this.applicationContext.toast(info) }