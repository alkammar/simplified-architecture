package com.plume.repository

import com.plume.entity.Motion
import infra.Repository

interface MotionRepository: Repository<Motion> {
    suspend fun enableMotion(enable: Boolean)
}