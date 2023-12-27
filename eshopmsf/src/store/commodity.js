import { reactive } from "vue";

let commodityStore = reactive({
    data: {
        commodity: [
            //id,name,price,quantity,number
        ]
    }
});

export { commodityStore }