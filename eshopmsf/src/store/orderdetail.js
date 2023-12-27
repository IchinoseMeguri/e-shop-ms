import { reactive } from "vue";

let orderDetailStore = reactive({
    data: {
        orderid: 1,
        time: "",
        orderDetail: [

        ]
    }
});

export { orderDetailStore }