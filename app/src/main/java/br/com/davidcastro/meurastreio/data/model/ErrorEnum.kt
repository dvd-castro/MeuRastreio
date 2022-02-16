package br.com.davidcastro.meurastreio.data.model

enum class ErrorEnum(val id: Int){
    ERROR_NAO_ENCONTRADO(1),
    ERROR_INSERIDO(2),
    ERROR_SERVER(3),
    ERROR_NETWORK(4)
}

