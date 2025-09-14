package com.omkero.asicscanner.ui.theme


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.PathFillType

val Cpu: ImageVector
    get() {
        if (CpuICon != null) return CpuICon!!

        CpuICon = ImageVector.Builder(
            name = "Cpu",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(5f, 0f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0.5f, 0.5f)
                verticalLineTo(2f)
                horizontalLineToRelative(1f)
                verticalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 1f, 0f)
                verticalLineTo(2f)
                horizontalLineToRelative(1f)
                verticalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 1f, 0f)
                verticalLineTo(2f)
                horizontalLineToRelative(1f)
                verticalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 1f, 0f)
                verticalLineTo(2f)
                arcTo(2.5f, 2.5f, 0f, false, true, 14f, 4.5f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, 1f)
                horizontalLineTo(14f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, 1f)
                horizontalLineTo(14f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, 1f)
                horizontalLineTo(14f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, 1f)
                horizontalLineTo(14f)
                arcToRelative(2.5f, 2.5f, 0f, false, true, -2.5f, 2.5f)
                verticalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, -1f, 0f)
                verticalLineTo(14f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, -1f, 0f)
                verticalLineTo(14f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, -1f, 0f)
                verticalLineTo(14f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(1.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, -1f, 0f)
                verticalLineTo(14f)
                arcTo(2.5f, 2.5f, 0f, false, true, 2f, 11.5f)
                horizontalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, -1f)
                horizontalLineTo(2f)
                verticalLineToRelative(-1f)
                horizontalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, -1f)
                horizontalLineTo(2f)
                verticalLineToRelative(-1f)
                horizontalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, -1f)
                horizontalLineTo(2f)
                verticalLineToRelative(-1f)
                horizontalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, false, true, 0f, -1f)
                horizontalLineTo(2f)
                arcTo(2.5f, 2.5f, 0f, false, true, 4.5f, 2f)
                verticalLineTo(0.5f)
                arcTo(0.5f, 0.5f, 0f, false, true, 5f, 0f)
                moveToRelative(-0.5f, 3f)
                arcTo(1.5f, 1.5f, 0f, false, false, 3f, 4.5f)
                verticalLineToRelative(7f)
                arcTo(1.5f, 1.5f, 0f, false, false, 4.5f, 13f)
                horizontalLineToRelative(7f)
                arcToRelative(1.5f, 1.5f, 0f, false, false, 1.5f, -1.5f)
                verticalLineToRelative(-7f)
                arcTo(1.5f, 1.5f, 0f, false, false, 11.5f, 3f)
                close()
                moveTo(5f, 6.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 6.5f, 5f)
                horizontalLineToRelative(3f)
                arcTo(1.5f, 1.5f, 0f, false, true, 11f, 6.5f)
                verticalLineToRelative(3f)
                arcTo(1.5f, 1.5f, 0f, false, true, 9.5f, 11f)
                horizontalLineToRelative(-3f)
                arcTo(1.5f, 1.5f, 0f, false, true, 5f, 9.5f)
                close()
                moveTo(6.5f, 6f)
                arcToRelative(0.5f, 0.5f, 0f, false, false, -0.5f, 0.5f)
                verticalLineToRelative(3f)
                arcToRelative(0.5f, 0.5f, 0f, false, false, 0.5f, 0.5f)
                horizontalLineToRelative(3f)
                arcToRelative(0.5f, 0.5f, 0f, false, false, 0.5f, -0.5f)
                verticalLineToRelative(-3f)
                arcToRelative(0.5f, 0.5f, 0f, false, false, -0.5f, -0.5f)
                close()
            }
        }.build()

        return CpuICon!!
    }

private var CpuICon: ImageVector? = null



val DashboardIcon: ImageVector
    get() {
        if (Dashboard != null) return Dashboard!!

        Dashboard = ImageVector.Builder(
            name = "Dashboard",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(520f, 360f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(240f)
                close()
                moveTo(120f, 520f)
                verticalLineToRelative(-400f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(400f)
                close()
                moveToRelative(400f, 320f)
                verticalLineToRelative(-400f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(400f)
                close()
                moveToRelative(-400f, 0f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(240f)
                close()
                moveToRelative(80f, -400f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-240f)
                horizontalLineTo(200f)
                close()
                moveToRelative(400f, 320f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-240f)
                horizontalLineTo(600f)
                close()
                moveToRelative(0f, -480f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-80f)
                horizontalLineTo(600f)
                close()
                moveTo(200f, 760f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-80f)
                horizontalLineTo(200f)
                close()
                moveToRelative(160f, -80f)
            }
        }.build()

        return Dashboard!!
    }

private var Dashboard: ImageVector? = null



val SettingsIcon: ImageVector
    get() {
        if (Settings != null) return Settings!!

        Settings = ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveToRelative(370f, -80f)
                lineToRelative(-16f, -128f)
                quadToRelative(-13f, -5f, -24.5f, -12f)
                reflectiveQuadTo(307f, 725f)
                lineToRelative(-119f, 50f)
                lineTo(78f, 585f)
                lineToRelative(103f, -78f)
                quadToRelative(-1f, -7f, -1f, -13.5f)
                verticalLineToRelative(-27f)
                quadToRelative(0f, -6.5f, 1f, -13.5f)
                lineTo(78f, 375f)
                lineToRelative(110f, -190f)
                lineToRelative(119f, 50f)
                quadToRelative(11f, -8f, 23f, -15f)
                reflectiveQuadToRelative(24f, -12f)
                lineToRelative(16f, -128f)
                horizontalLineToRelative(220f)
                lineToRelative(16f, 128f)
                quadToRelative(13f, 5f, 24.5f, 12f)
                reflectiveQuadToRelative(22.5f, 15f)
                lineToRelative(119f, -50f)
                lineToRelative(110f, 190f)
                lineToRelative(-103f, 78f)
                quadToRelative(1f, 7f, 1f, 13.5f)
                verticalLineToRelative(27f)
                quadToRelative(0f, 6.5f, -2f, 13.5f)
                lineToRelative(103f, 78f)
                lineToRelative(-110f, 190f)
                lineToRelative(-118f, -50f)
                quadToRelative(-11f, 8f, -23f, 15f)
                reflectiveQuadToRelative(-24f, 12f)
                lineTo(590f, 880f)
                close()
                moveToRelative(70f, -80f)
                horizontalLineToRelative(79f)
                lineToRelative(14f, -106f)
                quadToRelative(31f, -8f, 57.5f, -23.5f)
                reflectiveQuadTo(639f, 633f)
                lineToRelative(99f, 41f)
                lineToRelative(39f, -68f)
                lineToRelative(-86f, -65f)
                quadToRelative(5f, -14f, 7f, -29.5f)
                reflectiveQuadToRelative(2f, -31.5f)
                reflectiveQuadToRelative(-2f, -31.5f)
                reflectiveQuadToRelative(-7f, -29.5f)
                lineToRelative(86f, -65f)
                lineToRelative(-39f, -68f)
                lineToRelative(-99f, 42f)
                quadToRelative(-22f, -23f, -48.5f, -38.5f)
                reflectiveQuadTo(533f, 266f)
                lineToRelative(-13f, -106f)
                horizontalLineToRelative(-79f)
                lineToRelative(-14f, 106f)
                quadToRelative(-31f, 8f, -57.5f, 23.5f)
                reflectiveQuadTo(321f, 327f)
                lineToRelative(-99f, -41f)
                lineToRelative(-39f, 68f)
                lineToRelative(86f, 64f)
                quadToRelative(-5f, 15f, -7f, 30f)
                reflectiveQuadToRelative(-2f, 32f)
                quadToRelative(0f, 16f, 2f, 31f)
                reflectiveQuadToRelative(7f, 30f)
                lineToRelative(-86f, 65f)
                lineToRelative(39f, 68f)
                lineToRelative(99f, -42f)
                quadToRelative(22f, 23f, 48.5f, 38.5f)
                reflectiveQuadTo(427f, 694f)
                close()
                moveToRelative(42f, -180f)
                quadToRelative(58f, 0f, 99f, -41f)
                reflectiveQuadToRelative(41f, -99f)
                reflectiveQuadToRelative(-41f, -99f)
                reflectiveQuadToRelative(-99f, -41f)
                quadToRelative(-59f, 0f, -99.5f, 41f)
                reflectiveQuadTo(342f, 480f)
                reflectiveQuadToRelative(40.5f, 99f)
                reflectiveQuadToRelative(99.5f, 41f)
                moveToRelative(-2f, -140f)
            }
        }.build()

        return Settings!!
    }

private var Settings: ImageVector? = null

val ServerIcon: ImageVector
    get() {
        if (Server != null) return Server!!

        Server = ImageVector.Builder(
            name = "Server",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(1.333f, 2.667f)
                curveTo(1.333f, 1.194f, 4.318f, 0f, 8f, 0f)
                reflectiveCurveToRelative(6.667f, 1.194f, 6.667f, 2.667f)
                verticalLineTo(4f)
                curveToRelative(0f, 1.473f, -2.985f, 2.667f, -6.667f, 2.667f)
                reflectiveCurveTo(1.333f, 5.473f, 1.333f, 4f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(1.333f, 6.334f)
                verticalLineToRelative(3f)
                curveTo(1.333f, 10.805f, 4.318f, 12f, 8f, 12f)
                reflectiveCurveToRelative(6.667f, -1.194f, 6.667f, -2.667f)
                verticalLineTo(6.334f)
                arcToRelative(6.5f, 6.5f, 0f, false, true, -1.458f, 0.79f)
                curveTo(11.81f, 7.684f, 9.967f, 8f, 8f, 8f)
                reflectiveCurveToRelative(-3.809f, -0.317f, -5.208f, -0.876f)
                arcToRelative(6.5f, 6.5f, 0f, false, true, -1.458f, -0.79f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14.667f, 11.668f)
                arcToRelative(6.5f, 6.5f, 0f, false, true, -1.458f, 0.789f)
                curveToRelative(-1.4f, 0.56f, -3.242f, 0.876f, -5.21f, 0.876f)
                curveToRelative(-1.966f, 0f, -3.809f, -0.316f, -5.208f, -0.876f)
                arcToRelative(6.5f, 6.5f, 0f, false, true, -1.458f, -0.79f)
                verticalLineToRelative(1.666f)
                curveTo(1.333f, 14.806f, 4.318f, 16f, 8f, 16f)
                reflectiveCurveToRelative(6.667f, -1.194f, 6.667f, -2.667f)
                close()
            }
        }.build()

        return Server!!
    }

private var Server: ImageVector? = null


val PortNumberIcon: ImageVector
    get() {
        if (PortNumberVec != null) return PortNumberVec!!

        PortNumberVec = ImageVector.Builder(
            name = "Nest_secure_alarm",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(480f, 520f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(520f, 480f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(480f, 440f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(440f, 480f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(480f, 520f)
                moveToRelative(120f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(640f, 480f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(600f, 440f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(560f, 480f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(600f, 520f)
                moveToRelative(-240f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(400f, 480f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(360f, 440f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(320f, 480f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(360f, 520f)
                moveToRelative(120f, 120f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(520f, 600f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(480f, 560f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(440f, 600f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(480f, 640f)
                moveToRelative(120f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(640f, 600f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(600f, 560f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(560f, 600f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(600f, 640f)
                moveToRelative(-240f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(400f, 600f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(360f, 560f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(320f, 600f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(360f, 640f)
                moveToRelative(120f, -240f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(520f, 360f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(480f, 320f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(440f, 360f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(480f, 400f)
                moveToRelative(120f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(640f, 360f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(600f, 320f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(560f, 360f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(600f, 400f)
                moveToRelative(-240f, 0f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(400f, 360f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(360f, 320f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(320f, 360f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(360f, 400f)
                moveTo(480f, 880f)
                quadToRelative(-83f, 0f, -156f, -31.5f)
                reflectiveQuadTo(197f, 763f)
                reflectiveQuadToRelative(-85.5f, -127f)
                reflectiveQuadTo(80f, 480f)
                reflectiveQuadToRelative(31.5f, -156f)
                reflectiveQuadTo(197f, 197f)
                reflectiveQuadToRelative(127f, -85.5f)
                reflectiveQuadTo(480f, 80f)
                reflectiveQuadToRelative(156f, 31.5f)
                reflectiveQuadTo(763f, 197f)
                reflectiveQuadToRelative(85.5f, 127f)
                reflectiveQuadTo(880f, 480f)
                reflectiveQuadToRelative(-31.5f, 156f)
                reflectiveQuadTo(763f, 763f)
                reflectiveQuadToRelative(-127f, 85.5f)
                reflectiveQuadTo(480f, 880f)
                moveToRelative(0f, -80f)
                quadToRelative(133f, 0f, 226.5f, -93.5f)
                reflectiveQuadTo(800f, 480f)
                reflectiveQuadToRelative(-93.5f, -226.5f)
                reflectiveQuadTo(480f, 160f)
                reflectiveQuadToRelative(-226.5f, 93.5f)
                reflectiveQuadTo(160f, 480f)
                reflectiveQuadToRelative(93.5f, 226.5f)
                reflectiveQuadTo(480f, 800f)
                moveToRelative(0f, -320f)
            }
        }.build()

        return PortNumberVec!!
    }

private var PortNumberVec: ImageVector? = null



