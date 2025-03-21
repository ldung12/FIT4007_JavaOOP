import pygame
import time
import random
import heapq

# Khởi tạo Pygame
pygame.init()

# Cài đặt cửa sổ trò chơi
MAP_WIDTH, MAP_HEIGHT = 900, 900
SIDEBAR_WIDTH = 300
WIDTH, HEIGHT = MAP_WIDTH + SIDEBAR_WIDTH, MAP_HEIGHT
CELL_SIZE = 30
ROWS, COLS = MAP_HEIGHT // CELL_SIZE, MAP_WIDTH // CELL_SIZE
WIN = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Racing games")

# Màu sắc
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
YELLOW = (255, 255, 0)
PURPLE = (128, 0, 128)
ORANGE = (255, 165, 0)
GRAY = (200, 200, 200)
GREEN = (0, 255, 0)

# Điểm xuất phát và đích
START = (0, 0)
GOAL = (ROWS - 1, COLS - 1)

# Thời gian giới hạn (giây)
TIME_LIMIT = 30

# Thuật toán A* tìm đường đi ngắn nhất (hiển thị)
def a_star(start, goal, grid):
    def heuristic(a, b):
        return ((a[0] - b[0]) ** 2 + (a[1] - b[1]) ** 2) ** 0.5  # Khoảng cách Euclidean

    open_set = [(0, start)]
    came_from = {}
    g_score = {start: 0}
    f_score = {start: heuristic(start, goal)}

    while open_set:
        current = heapq.heappop(open_set)[1]

        if current == goal:
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.append(start)
            return path[::-1]

        for dx, dy in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
            neighbor = (current[0] + dx, current[1] + dy)
            if (0 <= neighbor[0] < ROWS and 0 <= neighbor[1] < COLS and grid[neighbor[0]][neighbor[1]] == 0):
                tentative_g_score = g_score[current] + 1
                if neighbor not in g_score or tentative_g_score < g_score[neighbor]:
                    came_from[neighbor] = current
                    g_score[neighbor] = tentative_g_score
                    f_score[neighbor] = g_score[neighbor] + heuristic(neighbor, goal)
                    heapq.heappush(open_set, (f_score[neighbor], neighbor))

    return None

# Tạo bản đồ ngẫu nhiên với chướng ngại vật, đảm bảo có đường đi
def generate_random_map():
    while True:
        grid = [[0 for _ in range(COLS)] for _ in range(ROWS)]
        obstacle_types = [
            {"value": 1, "color": BLACK, "size_range": (1, 1)},
            {"value": 2, "color": YELLOW, "size_range": (1, 2)},
            {"value": 3, "color": PURPLE, "size_range": (2, 3)},
            {"value": 4, "color": ORANGE, "size_range": (1, 3)}
        ]
        
        grid[START[0]][START[1]] = 0
        grid[GOAL[0]][GOAL[1]] = 0

        for _ in range(130):
            obstacle = random.choice(obstacle_types)
            size_w = random.randint(obstacle["size_range"][0], obstacle["size_range"][1])
            size_h = random.randint(obstacle["size_range"][0], obstacle["size_range"][1])
            x = random.randint(0, COLS - size_w)
            y = random.randint(0, ROWS - size_h)

            for i in range(y, min(y + size_h, ROWS)):
                for j in range(x, min(x + size_w, COLS)):
                    if (i, j) != START and (i, j) != GOAL:
                        grid[i][j] = obstacle["value"]

        # Kiểm tra xem có đường đi từ START đến GOAL không
        path = a_star(START, GOAL, grid)
        if path:  # Nếu có đường đi, trả về grid và obstacle_types
            return grid, obstacle_types
        # Nếu không có đường đi, tiếp tục tạo bản đồ mới

# Vẽ bản đồ
def draw_map(grid, obstacle_types, car_pos=None, path=None):
    WIN.fill(WHITE)
    for row in range(ROWS):
        for col in range(COLS):
            cell_value = grid[row][col]
            if cell_value != 0:
                for obs in obstacle_types:
                    if obs["value"] == cell_value:
                        pygame.draw.rect(WIN, obs["color"], (col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE))

    if path:
        for pos in path:
            pygame.draw.rect(WIN, GREEN, (pos[1] * CELL_SIZE, pos[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE))

    pygame.draw.rect(WIN, BLUE, (START[1] * CELL_SIZE, START[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE))
    pygame.draw.rect(WIN, RED, (GOAL[1] * CELL_SIZE, GOAL[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE))

    if car_pos:
        pygame.draw.rect(WIN, BLUE, (car_pos[1] * CELL_SIZE, car_pos[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE))

    pygame.draw.rect(WIN, GRAY, (MAP_WIDTH, 0, SIDEBAR_WIDTH, HEIGHT))
    pygame.display.update()

# Hàm ngắt dòng văn bản
def wrap_text(text, font, max_width):
    words = text.split(" ")
    lines = []
    current_line = ""
    
    for word in words:
        test_line = current_line + word + " "
        test_surface = font.render(test_line, True, BLACK)
        if test_surface.get_width() <= max_width:
            current_line = test_line
        else:
            lines.append(current_line.strip())
            current_line = word + " "
    lines.append(current_line.strip())
    return lines

# Main game loop
def main():
    clock = pygame.time.Clock()
    running = True
    grid, obstacle_types = generate_random_map()  # Đảm bảo bản đồ có đường đi
    car_pos = START
    start_time = time.time()
    score = 0

    # Tìm đường đi bằng A* để hiển thị
    path = a_star(START, GOAL, grid)

    last_move_time = 0
    move_delay = 0.15

    font = pygame.font.SysFont("Arial", 28)
    max_text_width = SIDEBAR_WIDTH - 20

    while running:
        clock.tick(60)
        current_time = time.time()
        elapsed_time = current_time - start_time
        remaining_time = max(0, TIME_LIMIT - elapsed_time)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_r:
                    grid, obstacle_types = generate_random_map()  # Đảm bảo bản đồ mới có đường đi
                    car_pos = START
                    start_time = time.time()
                    score = 0
                    path = a_star(START, GOAL, grid)

        # Người chơi tự điều khiển xe
        moved = False
        if current_time - last_move_time >= move_delay:
            keys = pygame.key.get_pressed()
            new_pos = car_pos
            if keys[pygame.K_UP] and car_pos[0] > 0:
                new_pos = (car_pos[0] - 1, car_pos[1])
            elif keys[pygame.K_DOWN] and car_pos[0] < ROWS - 1:
                new_pos = (car_pos[0] + 1, car_pos[1])
            elif keys[pygame.K_LEFT] and car_pos[1] > 0:
                new_pos = (car_pos[0], car_pos[1] - 1)
            elif keys[pygame.K_RIGHT] and car_pos[1] < COLS - 1:
                new_pos = (car_pos[0], car_pos[1] + 1)

            if (0 <= new_pos[0] < ROWS and 0 <= new_pos[1] < COLS):
                if grid[new_pos[0]][new_pos[1]] == 0:
                    if new_pos != car_pos:
                        car_pos = new_pos
                        score += 10
                        last_move_time = current_time
                        moved = True

        if car_pos == GOAL:
            print(f"Congratulations! You have completed in {elapsed_time:.2f} second. Point: {score}")
            running = False

        if remaining_time <= 0:
            print("Time is up! You lose!")
            running = False

        draw_map(grid, obstacle_types, car_pos, path)

        time_text = font.render(f"Time remaining: {remaining_time:.2f}s", True, BLACK)
        score_text = font.render(f"Point: {score}", True, BLACK)
        instructions = [
            "How to play:",
            "- Use arrow keys to move",
            "- To red destination",
            "- Press R to refresh",
            "- Goal: Get to the finish line as fast as possible in 30s"
        ]

        WIN.blit(time_text, (MAP_WIDTH + 10, 10))
        WIN.blit(score_text, (MAP_WIDTH + 10, 50))

        y_offset = 90
        for instruction in instructions:
            wrapped_lines = wrap_text(instruction, font, max_text_width)
            for line in wrapped_lines:
                text_surface = font.render(line, True, BLACK)
                WIN.blit(text_surface, (MAP_WIDTH + 10, y_offset))
                y_offset += 40

        pygame.display.update()

    pygame.quit()

if __name__ == "__main__":
    main()