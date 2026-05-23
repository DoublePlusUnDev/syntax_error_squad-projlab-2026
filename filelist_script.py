import os
import csv
import re
import subprocess
from datetime import datetime

def natural_key(s):
    return [int(text) if text.isdigit() else text.lower()
            for text in re.split(r'(\d+)', s)]

def get_git_creation_time(file_path):
    try:
        result = subprocess.run(
            ["git", "log", "--diff-filter=A", "--follow", "--format=%aI", "--", file_path],
            capture_output=True,
            text=True
        )
        dates = result.stdout.strip().split("\n")
        if dates and dates[0]:
            return dates[-1]
    except:
        pass
    return "N/A"

base_dirs = ["src", "resources", "out"]
project_root = "."

rows = []

for base in base_dirs:
    base_path = os.path.join(project_root, base)

    if not os.path.exists(base_path):
        continue

    for root, dirs, files in os.walk(base_path):
        for file in files:
            full_path = os.path.join(root, file)
            rel_path = os.path.relpath(full_path, project_root)

            stat = os.stat(full_path)
            size_str = f"{stat.st_size} B"

            git_created = get_git_creation_time(rel_path)

            if git_created != "N/A":
                dt = datetime.fromisoformat(git_created)
                git_created = dt.strftime("%Y. %m. %d. %H:%M")

            rows.append([
                base,
                rel_path,
                size_str,
                git_created
            ])

rows.sort(key=lambda x: (x[0], natural_key(x[1])))

with open("file_list.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["Folder", "Path", "Size", "Git Created"])
    writer.writerows(rows)

print("Lista kész: file_list.csv")