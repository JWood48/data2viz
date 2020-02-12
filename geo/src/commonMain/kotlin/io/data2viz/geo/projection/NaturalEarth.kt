/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.projection

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.EPSILON
import io.data2viz.math.rad
import kotlin.math.abs

fun naturalEarthProjection(init: Projection.() -> Unit = {}) =
    projection(NaturalEarthProjection()) {
        scale = 175.295
        init()
    }

/**
 * The Natural Earth [http://www.shadedrelief.com/NE_proj/]
 * projection is a pseudocylindrical projection designed by Tom Patterson.
 * It is neither conformal nor equal-area,
 * but appealing to the eye for small-scale maps of the whole world.
 */
class NaturalEarthProjection : Projector<GeoPoint, Point3D> {

    override fun invert(point: Point3D): GeoPoint {
        var newPhi = point.y
        var i = 25
        var delta:Double
        do {
            val phi2 = newPhi * newPhi
            val phi4 = phi2 * phi2
            delta =
                (newPhi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4))) - newPhi) /
                        (1.007226 + phi2 * (0.015085 * 3 + phi4 * (-0.044475 * 7 + 0.028874 * 9 * phi2 - 0.005916 * 11 * phi4)))
            newPhi -= delta
        } while (abs(delta) > EPSILON && --i > 0)
        val phi2 = newPhi * newPhi
        return GeoPoint(
            (point.x / (0.8707 + (phi2) * (-0.131979 + phi2 * (-0.013791 + phi2 * phi2 * phi2 * (0.003971 - 0.001529 * phi2))))).rad,
            newPhi.rad
        )
    }

    override fun project(point: GeoPoint): Point3D {
        val phi2 = point.lat.rad * point.lat.rad
        val phi4 = phi2 * phi2
        return Point3D(
            point.lon.rad * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4))),
            point.lat.rad * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)))
        )
    }

}