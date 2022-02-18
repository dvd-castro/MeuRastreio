package br.com.davidcastro.meurastreio.data.model

enum class MessageEnum(val id: Int){
    NOT_FOUND(1),
    ALREADY_INSERTED(2),
    SERVER_ERROR(3),
    NETWORK_ERROR(4),
    INSERTED_WITH_SUCESS(5),
    DELETE_ERROR(6)
}

