package com.example.data.mapper

import com.example.data.model.FeedbackModel
import com.example.data.model.InfoModel
import com.example.data.model.PriceModel
import com.example.data.model.ProductListModel
import com.example.data.model.ProductModel
import com.example.domain.model.Feedback
import com.example.domain.model.Info
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.model.ProductList


fun ProductListModel.toProductList() = ProductList(
    items
)

fun ProductModel.toProduct() = id?.let {
    Product(
        it, title, subtitle, price?.toPrice(), feedback?.toFeedBack(),
        tags, available, description, info?.map { it.toInfo() }, ingredients
    )
}


fun FeedbackModel.toFeedBack() = Feedback(
    count, rating
)

fun InfoModel.toInfo() = Info(
    title, value
)

fun PriceModel.toPrice(): Price {
    // Проверяем, что все поля не являются null
    if (price != null && discount != null && priceWithDiscount != null && unit != null) {
        return Price(price!!, discount!!, priceWithDiscount!!, unit!!)
    } else {
        // Если хотя бы одно из полей null, возвращаем пустой объект Price
        return Price("0", 0, "0", "") // Можете уточнить значения по умолчанию
    }
}