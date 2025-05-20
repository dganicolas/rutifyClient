package com.example.rutifyclient.utils

import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.domain.room.RutinaDtoRoom

fun MapearDeRutinaDtoARutinaDtoRoom(rutinaDTO: RutinaDTO): RutinaDtoRoom {
    return RutinaDtoRoom(
        id = rutinaDTO.id!!,
        nombre = rutinaDTO.nombre,
        descripcion = rutinaDTO.descripcion,
        imagen = rutinaDTO.imagen,
        equipo = rutinaDTO.equipo
    )
}