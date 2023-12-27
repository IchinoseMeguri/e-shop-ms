import { reactive } from 'vue'

let store = reactive({
    data: {
        id: 1,
        type: "管理员",
        name: "admin",
        pwd: "123456",
        phone: "123",
        address: "1234567"
    }
})

export { store }


