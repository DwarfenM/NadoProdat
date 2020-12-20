package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.tutor_item.view.*
import kz.sherua.nadoprodat.MainActivity
import kz.sherua.nadoprodat.R

class TutorAdapter(val ctx: Context, val viewPage: ViewPager2) : RecyclerView.Adapter<TutorHolder>() {

    val tutorIv = arrayListOf(
        R.drawable.ic_tut,
        R.drawable.ic_tut_1,
        R.drawable.ic_tut_2,
        R.drawable.ic_tut_3,
        R.drawable.ic_tut_4,
        R.drawable.ic_tut_5
    )

    fun moveNext() {
        viewPage.setCurrentItem(viewPage.getCurrentItem() + 1)
    }

    fun movePrevious() {
        viewPage.setCurrentItem(viewPage.getCurrentItem() - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorHolder =
        TutorHolder(LayoutInflater.from(parent.context).inflate(R.layout.tutor_item, parent, false))

    override fun getItemCount(): Int = tutorIv.size


    override fun onBindViewHolder(holder: TutorHolder, position: Int) {
        holder.ivTutor.setImageDrawable(ContextCompat.getDrawable(ctx, tutorIv[position]))
        holder.btnNext.setOnClickListener {
            if (position== itemCount -1  ) {
                val intent = Intent(ctx, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ctx.startActivity(intent)
            } else {
                moveNext()
            }
        }
        holder.btnFirstNext.setOnClickListener {
                moveNext()
        }
        holder.btnSkip.setOnClickListener {
            val intent = Intent(ctx, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(intent)
        }
        if (position == 0) {
            holder.btnFirstNext.visibility = View.VISIBLE
            holder.btnNext.visibility = View.GONE
            holder.btnSkip.visibility = View.GONE
        } else {
            holder.btnFirstNext.visibility = View.GONE
            holder.btnNext.visibility = View.VISIBLE
            holder.btnSkip.visibility = View.VISIBLE
        }
        if (position == 0 || position == 1) {
            holder.tutMain.setBackgroundColor(ContextCompat.getColor(ctx, R.color.white))
            holder.btnFirstNext.background = ContextCompat.getDrawable(ctx, R.drawable.rounded_rectangle_button)
            holder.btnNext.background = ContextCompat.getDrawable(ctx, R.drawable.rounded_rectangle_button)
            holder.btnSkip.setTextColor(ContextCompat.getColor(ctx,R.color.mainBlue))
            holder.btnNext.setTextColor(ContextCompat.getColor(ctx,R.color.white))
        } else {
            holder.tutMain.setBackgroundColor(ContextCompat.getColor(ctx, R.color.mainBlue))
            holder.btnNext.background = ContextCompat.getDrawable(ctx, R.drawable.rounded_rectangle)
            holder.btnNext.setTextColor(ContextCompat.getColor(ctx,R.color.mainBlue))
            holder.btnSkip.setTextColor(ContextCompat.getColor(ctx,R.color.white))
        }

//        holder.tutMain.background = ContextCompat.getDrawable(ctx, R.drawable.ic_tut)
    }

}

class TutorHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivTutor = view.ivTutor
    val tutMain = view.tutMain
    val btnNext = view.btnNext
    val btnFirstNext = view.btnNextFirst
    val btnSkip = view.btnSkip
}