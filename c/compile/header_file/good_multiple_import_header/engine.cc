#include "engine.h"
#include "render.h"
#include "ai.h"

void hostFrame(World& world)
{
    think(world);
    render(world);
}
