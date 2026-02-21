interface Person {
    name : string
    age : number
}

let user: Person = {
    name : "Boss",
    age : 20
}

function identity<T>(value: T): T {
    return value
}

identity<string>("hello")
identity<number>(123)

let update_user: Partial<Person> = {
    name: "boss"
}

