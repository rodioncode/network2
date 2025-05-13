package com.example.network1.libs


import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.network1.R

class ProductAdapter(
    private val items: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleTextView)
        val price: TextView = view.findViewById(R.id.priceTextView)
        val image: ImageView = view.findViewById(R.id.productImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]
        holder.title.text = product.title
        holder.price.text = "${product.price}"
        Glide.with(holder.itemView).load(product.image).into(holder.image)
        holder.itemView.setOnClickListener { onClick(product) }
    }

    override fun getItemCount() = items.size
}
