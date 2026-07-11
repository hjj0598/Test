const STORAGE_KEY = "fitlog-workouts-v1";

const form = document.querySelector("#workoutForm");
const recordId = document.querySelector("#recordId");
const dateInput = document.querySelector("#dateInput");
const typeInput = document.querySelector("#typeInput");
const nameInput = document.querySelector("#nameInput");
const durationInput = document.querySelector("#durationInput");
const weightInput = document.querySelector("#weightInput");
const setsInput = document.querySelector("#setsInput");
const repsInput = document.querySelector("#repsInput");
const bodyInput = document.querySelector("#bodyInput");
const notesInput = document.querySelector("#notesInput");
const resetBtn = document.querySelector("#resetBtn");
const seedBtn = document.querySelector("#seedBtn");
const exportBtn = document.querySelector("#exportBtn");
const importInput = document.querySelector("#importInput");
const workoutList = document.querySelector("#workoutList");
const emptyState = document.querySelector("#emptyState");
const workoutTemplate = document.querySelector("#workoutTemplate");
const filterType = document.querySelector("#filterType");
const searchInput = document.querySelector("#searchInput");
const formMode = document.querySelector("#formMode");
const trendChart = document.querySelector("#trendChart");
const pulseCanvas = document.querySelector("#pulseCanvas");
const insightText = document.querySelector("#insightText");
const chartTotal = document.querySelector("#chartTotal");
const chartUnit = document.querySelector("#chartUnit");
const chartActiveDays = document.querySelector("#chartActiveDays");
const chartBest = document.querySelector("#chartBest");

const statSessions = document.querySelector("#statSessions");
const statMinutes = document.querySelector("#statMinutes");
const statVolume = document.querySelector("#statVolume");
const statStreak = document.querySelector("#statStreak");
const weekTitle = document.querySelector("#weekTitle");
const todayStatus = document.querySelector("#todayStatus");
const chartButtons = document.querySelectorAll("[data-chart]");

let workouts = loadWorkouts();
let chartMetric = "duration";

dateInput.value = toInputDate(new Date());
render();
drawPulse();

form.addEventListener("submit", (event) => {
  event.preventDefault();

  const workout = {
    id: recordId.value || makeId(),
    date: dateInput.value,
    type: typeInput.value,
    name: nameInput.value.trim(),
    duration: Number(durationInput.value),
    weight: Number(weightInput.value) || 0,
    sets: Number(setsInput.value) || 0,
    reps: Number(repsInput.value) || 0,
    body: bodyInput.value,
    notes: notesInput.value.trim(),
    updatedAt: new Date().toISOString(),
  };

  if (!workout.name || !workout.date || !workout.duration) return;

  const existingIndex = workouts.findIndex((item) => item.id === workout.id);
  if (existingIndex >= 0) {
    workouts[existingIndex] = workout;
  } else {
    workouts.unshift(workout);
  }

  saveWorkouts();
  resetForm();
  render();
});

resetBtn.addEventListener("click", resetForm);
filterType.addEventListener("change", renderHistory);
searchInput.addEventListener("input", renderHistory);

seedBtn.addEventListener("click", () => {
  if (workouts.length && !confirm("演示数据会替换当前记录，继续吗？")) return;
  workouts = demoWorkouts();
  saveWorkouts();
  resetForm();
  render();
});

exportBtn.addEventListener("click", () => {
  const data = JSON.stringify({ exportedAt: new Date().toISOString(), workouts }, null, 2);
  const blob = new Blob([data], { type: "application/json" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.href = url;
  link.download = `fitlog-${toInputDate(new Date())}.json`;
  link.click();
  URL.revokeObjectURL(url);
});

importInput.addEventListener("change", async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  try {
    const text = await file.text();
    const parsed = JSON.parse(text);
    const imported = Array.isArray(parsed) ? parsed : parsed.workouts;
    if (!Array.isArray(imported)) throw new Error("Invalid data");

    workouts = imported.filter(isValidWorkout).map((item) => ({
      ...item,
      id: item.id || makeId(),
      duration: Number(item.duration) || 0,
      weight: Number(item.weight) || 0,
      sets: Number(item.sets) || 0,
      reps: Number(item.reps) || 0,
    }));
    saveWorkouts();
    render();
  } catch {
    alert("导入失败：请选择 FitLog 导出的 JSON 文件。");
  } finally {
    importInput.value = "";
  }
});

chartButtons.forEach((button) => {
  button.addEventListener("click", () => {
    chartMetric = button.dataset.chart;
    chartButtons.forEach((item) => item.classList.toggle("active", item === button));
    drawTrendChart();
  });
});

function render() {
  workouts.sort((a, b) => new Date(b.date) - new Date(a.date));
  renderStats();
  renderHistory();
  drawTrendChart();
  updateTodayStatus();
}

function renderStats() {
  const now = new Date();
  const weekStart = startOfWeek(now);
  const weekItems = workouts.filter((item) => new Date(`${item.date}T00:00:00`) >= weekStart);
  const minutes = sum(weekItems, "duration");
  const volume = weekItems.reduce((total, item) => total + getVolume(item), 0);
  const streak = calculateStreak(workouts);

  statSessions.textContent = weekItems.length;
  statMinutes.textContent = Math.round(minutes);
  statVolume.textContent = compactNumber(volume);
  statStreak.textContent = streak;

  if (weekItems.length === 0) {
    weekTitle.textContent = "本周还没开练";
  } else if (weekItems.length < 3) {
    weekTitle.textContent = "本周节奏已启动";
  } else {
    weekTitle.textContent = "本周训练很稳";
  }
}

function renderHistory() {
  const selectedType = filterType.value;
  const keyword = searchInput.value.trim().toLowerCase();
  const visibleItems = workouts.filter((item) => {
    const typeMatched = selectedType === "全部" || item.type === selectedType;
    const text = `${item.name} ${item.body} ${item.notes}`.toLowerCase();
    return typeMatched && text.includes(keyword);
  });

  workoutList.innerHTML = "";
  emptyState.hidden = visibleItems.length > 0;

  visibleItems.forEach((item) => {
    const node = workoutTemplate.content.firstElementChild.cloneNode(true);
    const badge = node.querySelector(".type-badge");
    const title = node.querySelector("h3");
    const date = node.querySelector(".workout-date");
    const meta = node.querySelector(".workout-meta");
    const notes = node.querySelector(".workout-notes");
    const editBtn = node.querySelector(".edit-btn");
    const deleteBtn = node.querySelector(".delete-btn");

    badge.textContent = typeShortName(item.type);
    badge.className = `type-badge ${typeClass(item.type)}`;
    title.textContent = item.name;
    date.textContent = formatDate(item.date);
    meta.textContent = buildMeta(item);
    notes.textContent = item.notes || "没有备注";

    editBtn.addEventListener("click", () => editWorkout(item.id));
    deleteBtn.addEventListener("click", () => deleteWorkout(item.id));
    workoutList.appendChild(node);
  });
}

function drawTrendChart() {
  const ctx = trendChart.getContext("2d");
  const ratio = window.devicePixelRatio || 1;
  const rect = trendChart.getBoundingClientRect();
  trendChart.width = Math.max(1, Math.floor(rect.width * ratio));
  trendChart.height = Math.floor(270 * ratio);
  ctx.scale(ratio, ratio);

  const width = rect.width;
  const height = 270;
  const padding = { top: 22, right: 18, bottom: 40, left: 38 };
  const days = lastNDays(14);
  const values = days.map((day) => {
    const items = workouts.filter((item) => item.date === day.key);
    return chartMetric === "duration"
      ? sum(items, "duration")
      : items.reduce((total, item) => total + getVolume(item), 0);
  });
  const max = Math.max(...values, chartMetric === "duration" ? 60 : 100);
  const chartWidth = width - padding.left - padding.right;
  const chartHeight = height - padding.top - padding.bottom;
  const barWidth = Math.max(9, chartWidth / days.length - 9);
  const total = values.reduce((acc, value) => acc + value, 0);
  const activeDays = values.filter(Boolean).length;
  const best = Math.max(...values, 0);
  const label = chartMetric === "duration" ? "分钟" : "kg";

  chartTotal.textContent = compactNumber(total);
  chartUnit.textContent = chartMetric === "duration" ? "分钟累计" : "kg 累计";
  chartActiveDays.textContent = activeDays;
  chartBest.textContent = compactNumber(best);

  ctx.clearRect(0, 0, width, height);
  const bg = ctx.createLinearGradient(0, 0, width, height);
  bg.addColorStop(0, "#fffdf8");
  bg.addColorStop(1, "#f0f6f2");
  ctx.fillStyle = bg;
  roundRect(ctx, 0, 0, width, height, 8);
  ctx.fill();

  ctx.strokeStyle = "rgba(23, 27, 29, 0.08)";
  ctx.lineWidth = 1;
  for (let i = 0; i <= 4; i += 1) {
    const y = padding.top + (chartHeight / 4) * i;
    ctx.beginPath();
    ctx.moveTo(padding.left, y);
    ctx.lineTo(width - padding.right, y);
    ctx.stroke();
  }

  if (!activeDays) {
    drawEmptyChart(ctx, width, height, padding, chartWidth, chartHeight);
  }

  values.forEach((value, index) => {
    const x = padding.left + index * (chartWidth / days.length) + 4;
    const barHeight = (value / max) * chartHeight;
    const y = padding.top + chartHeight - barHeight;
    const gradient = ctx.createLinearGradient(0, y, 0, height - padding.bottom);
    gradient.addColorStop(0, chartMetric === "duration" ? "#1f7a68" : "#d85f4f");
    gradient.addColorStop(1, chartMetric === "duration" ? "#8fd8bf" : "#f0b29f");
    ctx.shadowColor = chartMetric === "duration" ? "rgba(15, 138, 114, 0.22)" : "rgba(216, 95, 79, 0.22)";
    ctx.shadowBlur = value ? 16 : 0;
    ctx.shadowOffsetY = value ? 8 : 0;
    roundRect(ctx, x, y, barWidth, Math.max(5, barHeight), 7);
    ctx.fillStyle = gradient;
    ctx.fill();
    ctx.shadowColor = "transparent";
    ctx.shadowBlur = 0;
    ctx.shadowOffsetY = 0;

    if (index % 3 === 0 || index === days.length - 1) {
      ctx.fillStyle = "#70757d";
      ctx.font = "12px Microsoft YaHei, Arial";
      ctx.textAlign = "center";
      ctx.fillText(days[index].label, x + barWidth / 2, height - 10);
    }
  });

  insightText.textContent = activeDays
    ? `最近 14 天有 ${activeDays} 天训练，累计 ${compactNumber(total)} ${label}。`
    : "最近 14 天还没有记录，今天可以先来一条轻量训练。";
}

function drawEmptyChart(ctx, width, height, padding, chartWidth, chartHeight) {
  const baseY = padding.top + chartHeight;
  const ghostValues = [0.28, 0.44, 0.32, 0.62, 0.4, 0.52, 0.34, 0.72, 0.5, 0.38, 0.58, 0.46, 0.7, 0.48];
  const step = chartWidth / ghostValues.length;
  const barWidth = Math.max(9, step - 9);

  ghostValues.forEach((value, index) => {
    const x = padding.left + index * step + 4;
    const barHeight = value * chartHeight * 0.56;
    const y = baseY - barHeight;
    roundRect(ctx, x, y, barWidth, barHeight, 7);
    ctx.fillStyle = index % 3 === 0 ? "rgba(15, 138, 114, 0.14)" : "rgba(23, 27, 29, 0.07)";
    ctx.fill();
  });

  ctx.fillStyle = "rgba(255, 255, 255, 0.82)";
  roundRect(ctx, width * 0.2, height * 0.34, width * 0.6, 68, 8);
  ctx.fill();
  ctx.strokeStyle = "rgba(15, 138, 114, 0.2)";
  ctx.stroke();
  ctx.fillStyle = "#25302d";
  ctx.font = "700 15px Microsoft YaHei, Arial";
  ctx.textAlign = "center";
  ctx.fillText("从第一条记录开始生成趋势", width / 2, height * 0.34 + 30);
  ctx.fillStyle = "#697179";
  ctx.font = "12px Microsoft YaHei, Arial";
  ctx.fillText("保存训练后，14 天图表会自动更新", width / 2, height * 0.34 + 52);
}

function drawPulse() {
  const ctx = pulseCanvas.getContext("2d");
  const ratio = window.devicePixelRatio || 1;
  const rect = pulseCanvas.getBoundingClientRect();
  pulseCanvas.width = Math.max(1, Math.floor(rect.width * ratio));
  pulseCanvas.height = Math.floor(rect.height * ratio);
  ctx.scale(ratio, ratio);

  const width = rect.width;
  const height = rect.height;
  ctx.clearRect(0, 0, width, height);
  ctx.fillStyle = "#13201d";
  ctx.fillRect(0, 0, width, height);

  ctx.strokeStyle = "rgba(136, 214, 200, 0.28)";
  ctx.lineWidth = 1;
  for (let x = 0; x < width; x += 18) {
    ctx.beginPath();
    ctx.moveTo(x, 0);
    ctx.lineTo(x, height);
    ctx.stroke();
  }

  ctx.strokeStyle = "#f3c85b";
  ctx.lineWidth = 4;
  ctx.lineJoin = "round";
  ctx.beginPath();
  const points = [
    [0, 52],
    [28, 52],
    [42, 36],
    [54, 62],
    [70, 18],
    [88, 68],
    [106, 44],
    [132, 44],
    [148, 52],
    [width, 52],
  ];
  points.forEach(([x, y], index) => {
    if (index === 0) ctx.moveTo(x, y);
    else ctx.lineTo(x, y);
  });
  ctx.stroke();
}

function editWorkout(id) {
  const item = workouts.find((workout) => workout.id === id);
  if (!item) return;

  recordId.value = item.id;
  dateInput.value = item.date;
  typeInput.value = item.type;
  nameInput.value = item.name;
  durationInput.value = item.duration;
  weightInput.value = item.weight || "";
  setsInput.value = item.sets || "";
  repsInput.value = item.reps || "";
  bodyInput.value = item.body || "全身";
  notesInput.value = item.notes || "";
  formMode.textContent = "编辑";
  document.querySelector("#record").scrollIntoView({ behavior: "smooth", block: "start" });
}

function deleteWorkout(id) {
  const item = workouts.find((workout) => workout.id === id);
  if (!item) return;
  const confirmed = confirm(`删除「${item.name}」这条记录吗？`);
  if (!confirmed) return;

  workouts = workouts.filter((workout) => workout.id !== id);
  saveWorkouts();
  render();
}

function resetForm() {
  form.reset();
  recordId.value = "";
  dateInput.value = toInputDate(new Date());
  formMode.textContent = "新建";
}

function updateTodayStatus() {
  const today = toInputDate(new Date());
  const todayItems = workouts.filter((item) => item.date === today);
  if (!todayItems.length) {
    todayStatus.textContent = "准备开练";
    return;
  }

  const minutes = sum(todayItems, "duration");
  todayStatus.textContent = `已练 ${minutes} 分钟`;
}

function demoWorkouts() {
  const today = new Date();
  const entries = [
    ["胸肩推", "力量", "胸", 68, 52.5, 16, 128, "卧推最后两组速度不错。"],
    ["轻松跑", "有氧", "全身", 36, 0, 0, 5.2, "配速稳定，心率控制住了。"],
    ["腿部训练", "力量", "腿", 74, 80, 18, 144, "深蹲注意膝盖轨迹。"],
    ["背部拉", "力量", "背", 62, 55, 15, 126, "引体向上多做了一组。"],
    ["髋肩灵活", "拉伸", "全身", 28, 0, 0, 0, "训练后舒展，睡前完成。"],
    ["综合循环", "综合", "核心", 45, 24, 12, 90, "壶铃摆动加平板支撑。"],
  ];

  return entries.map((entry, index) => {
    const date = new Date(today);
    date.setDate(today.getDate() - index * 2);
    return {
      id: makeId(),
      name: entry[0],
      type: entry[1],
      body: entry[2],
      duration: entry[3],
      weight: entry[4],
      sets: entry[5],
      reps: entry[6],
      notes: entry[7],
      date: toInputDate(date),
      updatedAt: new Date().toISOString(),
    };
  });
}

function loadWorkouts() {
  try {
    const stored = JSON.parse(localStorage.getItem(STORAGE_KEY) || "[]");
    return Array.isArray(stored) ? stored.filter(isValidWorkout) : [];
  } catch {
    return [];
  }
}

function saveWorkouts() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(workouts));
}

function makeId() {
  if (crypto.randomUUID) return crypto.randomUUID();
  return `fitlog-${Date.now()}-${Math.random().toString(16).slice(2)}`;
}

function isValidWorkout(item) {
  return item && item.date && item.name && Number(item.duration) >= 0;
}

function getVolume(item) {
  return (Number(item.weight) || 0) * (Number(item.sets) || 0) * (Number(item.reps) || 0);
}

function sum(items, key) {
  return items.reduce((total, item) => total + (Number(item[key]) || 0), 0);
}

function calculateStreak(items) {
  const dates = new Set(items.map((item) => item.date));
  let streak = 0;
  const cursor = new Date();

  while (dates.has(toInputDate(cursor))) {
    streak += 1;
    cursor.setDate(cursor.getDate() - 1);
  }

  return streak;
}

function buildMeta(item) {
  const parts = [`${item.type}`, `${item.body || "全身"}`, `${item.duration} 分钟`];
  const volume = getVolume(item);
  if (item.weight) parts.push(`${item.weight} kg`);
  if (item.sets) parts.push(`${item.sets} 组`);
  if (item.reps) parts.push(`${item.reps} 次/km`);
  if (volume) parts.push(`容量 ${compactNumber(volume)} kg`);
  return parts.join(" · ");
}

function typeShortName(type) {
  return { 力量: "力", 有氧: "氧", 拉伸: "伸", 综合: "综" }[type] || "练";
}

function typeClass(type) {
  return { 有氧: "cardio", 拉伸: "mobility", 综合: "mixed" }[type] || "";
}

function startOfWeek(date) {
  const clone = new Date(date);
  const day = clone.getDay() || 7;
  clone.setHours(0, 0, 0, 0);
  clone.setDate(clone.getDate() - day + 1);
  return clone;
}

function lastNDays(count) {
  return Array.from({ length: count }, (_, index) => {
    const date = new Date();
    date.setDate(date.getDate() - (count - 1 - index));
    return {
      key: toInputDate(date),
      label: `${date.getMonth() + 1}/${date.getDate()}`,
    };
  });
}

function toInputDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

function formatDate(value) {
  const date = new Date(`${value}T00:00:00`);
  return `${date.getMonth() + 1}月${date.getDate()}日`;
}

function compactNumber(value) {
  return new Intl.NumberFormat("zh-CN", { maximumFractionDigits: 1, notation: "compact" }).format(value || 0);
}

function roundRect(ctx, x, y, width, height, radius) {
  const r = Math.min(radius, width / 2, height / 2);
  ctx.beginPath();
  ctx.moveTo(x + r, y);
  ctx.arcTo(x + width, y, x + width, y + height, r);
  ctx.arcTo(x + width, y + height, x, y + height, r);
  ctx.arcTo(x, y + height, x, y, r);
  ctx.arcTo(x, y, x + width, y, r);
  ctx.closePath();
}

window.addEventListener("resize", () => {
  drawTrendChart();
  drawPulse();
});
