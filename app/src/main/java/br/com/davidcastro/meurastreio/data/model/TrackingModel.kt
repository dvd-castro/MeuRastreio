package br.com.davidcastro.meurastreio.data.model

data class TrackingModel(
    val objetos: List<Objeto>?,
    val quantidade: Int?,
    val versao: String?
) {
    fun isValidTracking(): Boolean? =
        this.objetos?.first()?.eventos?.isNotEmpty()

    fun getMessage() : String? =
        this.objetos?.first()?.mensagem
}

data class Objeto(
    val bloqueioObjeto: Boolean?,
    val codObjeto: String?,
    val eventos: List<Evento>?,
    val habilitaAutoDeclaracao: Boolean?,
    val habilitaCrowdshipping: Boolean?,
    val habilitaLocker: Boolean?,
    val habilitaPercorridaCarteiro: Boolean?,
    val modalidade: String?,
    val permiteEncargoImportacao: Boolean?,
    val possuiLocker: Boolean?,
    val mensagem: String?,
    val tipoPostal: TipoPostal?
)

data class Evento(
    val codigo: String?,
    val descricao: String?,
    val dtHrCriado: String?,
    val tipo: String?,
    val unidade: Unidade?,
    val unidadeDestino: UnidadeDestino?,
    val urlIcone: String?
)

data class TipoPostal(
    val categoria: String?,
    val descricao: String?,
    val sigla: String?
)

data class Unidade(
    val codSro: String?,
    val endereco: Endereco?,
    val nome: String?,
    val tipo: String?
)

data class UnidadeDestino(
    val endereco: Endereco?,
    val tipo: String?
)

data class Endereco(
    val bairro: String?,
    val cep: String?,
    val cidade: String?,
    val logradouro: String?,
    val numero: String?,
    val uf: String?
)