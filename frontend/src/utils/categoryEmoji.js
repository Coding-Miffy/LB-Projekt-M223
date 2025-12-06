// Eine Zuordnungstabelle (Mapping) von Kategorietexten zu passenden Emojis.
// Mehrere Varianten (z. B. mit Leerzeichen oder Emoji im Text) werden unterstützt,
// um möglichst viele Schreibweisen korrekt zu erfassen.
const emojiMap = {
    wildfires: '🔥',
    wildfire: '🔥',
    '🔥 wildfire': '🔥',

    severestorms: '🌪️',
    'severe storms': '🌪️',
    '🌪️ severe storm': '🌪️',

    volcanoes: '🌋',
    volcano: '🌋',
    '🌋 volcanoe': '🌋',

    sealakeice: '🧊',
    'seaandlakeice': '🧊',
    'sea lake ice': '🧊',
    '🧊 sea lake ice': '🧊',

    earthquakes: '🌍',
    earthquake: '🌍',
    '🌍 earthquake': '🌍',

    floods: '🌊',
    flood: '🌊',
    '🌊 flood': '🌊',

    landslides: '⛰️',
    landslide: '⛰️',
    '⛰️ landslide': '⛰️',

    snow: '❄️',
    '❄️ snow': '❄️',

    drought: '☀️',
    '☀️ drought': '☀️',

    dusthaze: '🌫️',
    'dust haze': '🌫️',
    'dustandhaze': '🌫️',
    '🌫️ dust haze': '🌫️',

    manmade: '🏗️',
    '🏗️ manmade': '🏗️',

    watercolor: '💧',
    'water color': '💧',
    '💧 water color': '💧',
};

// Die Funktion nimmt eine Kategorie (z. B. 'volcanoes') entgegen
// und gibt das passende Emoji zurück.
// Wenn kein Treffer gefunden wird, wird '❓' als Fallback verwendet.
const categoryEmoji = (category) => {
    if (!category) return '❓'; // Falls keine Kategorie übergeben wurde
    // Normalisiert den String, indem Leerzeichen entfernt und Kleinbuchstaben erzwungen werden
    return emojiMap[category.toLowerCase().replace(/\s/g, '')] || '❓';
};

export default categoryEmoji;