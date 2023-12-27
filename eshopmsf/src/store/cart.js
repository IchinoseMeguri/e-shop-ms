import { reactive } from "vue";

let cartStore = reactive({
    data: {
        price: 0,
        item: [
            //id,name,price,quantity,number
        ]
    }
});

export { cartStore }