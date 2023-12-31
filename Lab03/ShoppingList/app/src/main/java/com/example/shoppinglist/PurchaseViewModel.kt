package com.example.shoppinglist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PurchaseViewModel : ViewModel() {
    private var _items = setOf<PurchasableItem>()
    val itemsLiveData = MutableLiveData<Set<PurchasableItem>>()

    fun getPurchasedNum(): Int{
        return _items.count {it.purchased }
    }

    fun getTotalItems(): Int{
        return _items.count()
    }

    fun getProgress(): Float{

        if(getPurchasedNum()==0 && getTotalItems()==0) {
            Log.d("Progress", "getProgress > Current progress is 0")
            return 0f
        }
        else {
            Log.d("Progress", "getProgress > Current progress is ${(getPurchasedNum().toFloat() / getTotalItems())}")
            return getPurchasedNum().toFloat() / getTotalItems()
        }
    }

    fun getCategories() : List<ItemCategory>{
        return _items.map{it.category}.toSet().sorted()
    }

    fun addItem(newItem : PurchasableItem){
        Log.d("ItemMgm", "addItem > Trying to add new Item ($newItem)")

        _items = _items +newItem
        itemsLiveData.value = _items

        Log.d("ItemMgm", "addItem > Updated item list is $_items\nand related live data is ${itemsLiveData.value}")
    }

    fun getItems(): List<PurchasableItem>{
        return itemsLiveData.value?.toList() ?: listOf()
    }

    fun clearItems(){
        _items = emptySet()
        itemsLiveData.value = _items
    }

    fun deleteItem(target : PurchasableItem){
        Log.d("ItemMgm", "deleteItem > Trying to delete $target\n\tOld item list is $_items\n\tAnd ${itemsLiveData.value}")

        var flag : Boolean

        Log.d("ItemMgm", "deleteItem > Found = ${_items.find {it.name == target.name }}")


        _items  = _items.filter{it.name != target.name }.toSet()

        itemsLiveData.value = _items
        //flag = _items.remove(target)

       // Log.d("ItemMgm", "deleteItem > [$flag] Updated item list is $_items\nAnd related live data is ${_itemsLiveData.value}")

    }

    fun setPurchase(item: PurchasableItem, p: Boolean){
        _items = _items.map { it ->
            if (it.name == item.name)
                PurchasableItem(it.name, it.category, p)
            else
                it
        }.toSet()

        itemsLiveData.value = _items
    }

    fun setPurchase(item: PurchasableItem){

        var p = _items.find{it->it.name==item.name}?.purchased ?: false

        _items = _items.map { it ->
            if (it.name == item.name)
                PurchasableItem(it.name, it.category, !p)
            else
                it
        }.toSet()

        itemsLiveData.value = _items
    }
}