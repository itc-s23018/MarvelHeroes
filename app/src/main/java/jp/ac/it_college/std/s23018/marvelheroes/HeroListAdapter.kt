package jp.ac.it_college.std.s23018.marvelheroes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23018.marvelheroes.databinding.ActivityMainBinding
import jp.ac.it_college.std.s23018.marvelheroes.databinding.HeroRowBinding

class HeroListAdapter (
   private val data: List<Hero>) :
RecyclerView.Adapter<HeroListAdapter.ViewHolder>(){

    class ViewHolder(private val binding: HeroRowBinding) :
            RecyclerView.ViewHolder(binding.root) {
                var onItemClick: (Hero) -> Unit = {}

                fun bind(item: Hero) {
                    binding.apply {
                        heroName.text = item.name
                        root.setOnClickListener{
                            onItemClick(item)
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(HeroRowBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


}
