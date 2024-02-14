package com.monsjoker.namadaexplorer.data.network.id_namada_red

import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.ProposalModels
import retrofit2.http.GET

interface ItNamadaRedNetwork {
    @GET("chain/governance/proposals?fbclid=IwAR25u_nX9gLV6ZWP0hEvBIrorUvnb4wyx6K81bo5mGVtX6I7pWK6uhDmat0")
    suspend fun fetchProposals(): ProposalModels
}