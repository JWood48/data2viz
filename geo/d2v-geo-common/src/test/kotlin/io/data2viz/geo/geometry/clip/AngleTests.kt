package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.checkProject
import io.data2viz.geo.projection.gnomonicProjection
import io.data2viz.geo.projection.inDelta
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class AngleTests : TestBase() {

    

    @Test
    fun angle_defaults_to_zero() {
        val projection = gnomonicProjection {
            scale = 1.0
            translate(0.0, 0.0)
            anglePreClip  = 0.0.deg
        }

        checkProject(projection, 0.0, 0.0, 0.0, 0.0)
        checkProject(projection, 10.0, 0.0, 0.017632698070846498, 0.0)
        checkProject(projection, -10.0, 0.0, -0.017632698070846498, 0.0)
        checkProject(projection, 0.0, 10.0, 0.0, -0.017632698070846498)
        checkProject(projection, 0.0, -10.0, 0.0, 0.017632698070846498)
        checkProject(projection, 10.0, 10.0, 0.17632698070846495, -0.17904710860483972)
        checkProject(projection, 10.0, -10.0, 0.17632698070846495, 0.17904710860483972)
        checkProject(projection, -10.0, 10.0, -0.17632698070846495, -0.17904710860483972)
        checkProject(projection, -10.0, -10.0, -0.17632698070846495, 0.17904710860483972)

    }

    @Test
    fun angle_rotates_by_30_degrees_after_projecting() {
        val projection = gnomonicProjection {
            scale = 1.0
            translate(0.0, 0.0)
            anglePreClip = 30.0.deg
        }

        inDelta(projection.anglePreClip!!.deg, 30.0)

        checkProject(projection, 0.0, 0.0, 0.0, 0.0)
        checkProject(projection, 10.0, 0.0, 0.1527036446661393, -0.08816349035423247)
        checkProject(projection, -10.0, 0.0, -0.1527036446661393, 0.08816349035423247)
        checkProject(projection, 0.0, 10.0, -0.08816349035423247, -0.1527036446661393)
        checkProject(projection, 0.0, -10.0, 0.08816349035423247, 0.1527036446661393)
        checkProject(projection, 10.0, 10.0, 0.06318009036371944, -0.24322283488017502)
        checkProject(projection, 10.0, -10.0, 0.24222719896855913, 0.0668958541717101)
        checkProject(projection, -10.0, 10.0, -0.24222719896855913, -0.0668958541717101)
        checkProject(projection, -10.0, -10.0, -0.06318009036371944, 0.24322283488017502)

    }

    @Test
    fun angle_rotates_by_minus_30_degrees_after_projecting() {
        val projection = gnomonicProjection {
            scale = 1.0
            translate(0.0, 0.0)
            anglePreClip = (-30.0).deg
        }

        inDelta(projection.anglePreClip!!.deg, -30.0)

        checkProject(projection, 0.0, 0.0, 0.0, 0.0)
        checkProject(projection, 10.0, 0.0, 0.1527036446661393, 0.08816349035423247)
        checkProject(projection, -10.0, 0.0, -0.1527036446661393, -0.08816349035423247)
        checkProject(projection, 0.0, 10.0, 0.08816349035423247, -0.1527036446661393)
        checkProject(projection, 0.0, -10.0, -0.08816349035423247, 0.1527036446661393)
        checkProject(projection, 10.0, 10.0, 0.24222719896855913, -0.0668958541717101)
        checkProject(projection, 10.0, -10.0, 0.06318009036371944, 0.24322283488017502)
        checkProject(projection, -10.0, 10.0, -0.06318009036371944, -0.24322283488017502)
        checkProject(projection, -10.0, -10.0, -0.24222719896855913, 0.0668958541717101)

    }


    @Test
    fun angle_wraps_around_360() {
        val projection = gnomonicProjection {
            scale = 1.0
            translate(0.0, 0.0)
            anglePreClip = 360.0.deg
        }

        inDelta(projection.anglePreClip!!.deg, 0.0)

    }
}