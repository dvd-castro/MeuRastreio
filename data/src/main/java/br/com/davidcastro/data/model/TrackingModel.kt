package br.com.davidcastro.data.model

data class TrackingModel(
    private val objetos: List<Objeto>?,
) {
    fun isValidTracking(): Boolean? = this.objetos?.first()?.eventos?.isNotEmpty()

    fun getMessage(): String? = this.objetos?.first()?.mensagem

    fun getEvents(): List<Evento>? = this.objetos?.first()?.eventos

    fun getLastEvent(): Evento? = getEvents()?.first()

    fun getCode(): String? = this.objetos?.first()?.codObjeto

    fun getLastUnity(): Unidade? = getEvents()?.first()?.unidade

    fun getLastDestiny(): UnidadeDestino? = getEvents()?.first()?.unidadeDestino

    fun getLogistic(): String {
        val unidade = getLastUnity()?.endereco
        val nomeUnidade = getLastUnity()?.nome
        val unidadeDestino = getLastDestiny()?.endereco
        var result = ""

        if(nomeUnidade != null) {
            result = nomeUnidade
        } else {
            if(unidade != null && unidadeDestino != null) {
                result =  "De: ${getLastUnity()?.tipo} - ${unidade.cidade}/${unidade.uf}${System.lineSeparator()}Para: ${getLastDestiny()?.tipo} - ${unidadeDestino.cidade}/${unidadeDestino.uf}"
            } else if(unidade != null){
                result = "${getLastUnity()?.tipo} - ${unidade.cidade}/${unidade.uf}"
            }
        }

        return result
    }

    fun toTrackingHome(): TrackingHome =
        TrackingHome(
            code = this.getCode(),
            name = "",
            lastStatus = this.getEvents()?.first()?.descricao,
            date = getEvents()?.first()?.dtHrCriado,
            local = getLogistic(),
            hasUpdated = false,
            hasCompleted = false
        )
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