package com.kotlinshopper.kotlinshopper

import com.netflix.dgs.codegen.generated.types.Product
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@DgsComponent
class ProductDataFetcher {
    private final var apiClient: APIClient
    private final val baseUrl: String = "https://fakestoreapi.com"

    init {
        apiClient = APIClient(baseUrl)
    }

    @DgsQuery
    fun products(): List<Product> {
        val responseMono = apiClient.get("/products", null)
        val products = responseMono.bodyToFlux(Product::class.java)
        return products.collectList().block()!!
    }

    @DgsQuery
    fun product(@InputArgument id: String): Product {
        val responseMono = apiClient.get("/products/$id", null)
        val product = responseMono.bodyToMono(Product::class.java)
        return product.block()!!
    }

    @DgsMutation
    fun addProduct(@InputArgument product: Product): Product {
        val data: MultiValueMap<String, String> = LinkedMultiValueMap()
        data.add("title", product.title)
        data.add("price", product.price.toString())
        data.add("description", product.description)
        data.add("category", product.category)
        data.add("image", product.image)

        val responseMono = apiClient.post(uri = "/products", headers = null, data = data)
        val newProduct = responseMono.bodyToMono(Product::class.java)
        return newProduct.block()!!
    }

    @DgsMutation
    fun updateProduct(@InputArgument product: Product): Product {
        val data: MultiValueMap<String, String> = LinkedMultiValueMap()
        data.add("title", product.title)
        data.add("price", product.price.toString())
        data.add("description", product.description)
        data.add("category", product.category)
        data.add("image", product.image)

        val responseMono =
            apiClient.put(uri = "/products/${product.id}", headers = null, data = data)
        val updatedProduct = responseMono.bodyToMono(Product::class.java)
        return updatedProduct.block()!!
    }
    
    @DgsMutation
    fun deleteProduct(@InputArgument id: String): Product {
        val responseMono = apiClient.delete(uri = "/products/$id", headers = null)
        val product = responseMono.bodyToMono(Product::class.java)
        return product.block()!!
    }
}
