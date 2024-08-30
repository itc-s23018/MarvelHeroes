package jp.ac.it_college.std.s23018.marvelheroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeroListAdapter(private val heroList: List<Hero>)
    : RecyclerView.Adapter<HeroListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val heroName: TextView = view.findViewById(R.id.heroName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hero_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroList[position]
        holder.heroName.text = hero.name

        holder.itemView.setOnClickListener {
            (holder.itemView.context as? MainActivity)?.receiveHeroInfo(hero)
        }
    }

    override fun getItemCount() = heroList.size
}
