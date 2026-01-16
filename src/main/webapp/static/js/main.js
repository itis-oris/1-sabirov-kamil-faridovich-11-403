
    document.addEventListener('DOMContentLoaded', function() {
    const dateElements = document.querySelectorAll('.date-format');

    dateElements.forEach(element => {
    try {
    const originalDate = element.getAttribute('data-date');
    if (originalDate) {
    const date = new Date(originalDate);
    // Форматируем дату и время: "17 ноября 2025, 15:30"
    const formattedDate = date.toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'long',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
});
    element.textContent = formattedDate;
}
} catch (error) {
    console.error('Error formatting date:', error);
}
});
});
        document.addEventListener('DOMContentLoaded', function() {
        const numberInput = document.getElementById('number');
        const regionInput = document.getElementById('region');

        const cyrillicToLatin = {
        'А': 'A', 'В': 'B', 'Е': 'E', 'К': 'K', 'М': 'M',
        'Н': 'H', 'О': 'O', 'Р': 'P', 'С': 'C', 'Т': 'T',
        'У': 'Y', 'Х': 'X',
        'а': 'A', 'в': 'B', 'е': 'E', 'к': 'K', 'м': 'M',
        'н': 'H', 'о': 'O', 'р': 'P', 'с': 'C', 'т': 'T',
        'у': 'Y', 'х': 'X'
    };

        function formatNumber(input) {
        let value = input.value.toUpperCase();
        let formatted = '';

        for (let char of value) {
        if (cyrillicToLatin[char]) {
        formatted += cyrillicToLatin[char];
    } else if (/[A-Z0-9]/.test(char)) {
        formatted += char;
    }
    }

        if (formatted.length > 6) {
        formatted = formatted.substring(0, 6);
    }
        input.value = formatted;
    }

        function formatRegion(input) {
        let value = input.value;
        let formatted = '';

        for (let char of value) {
        if (/\d/.test(char)) {
        formatted += char;
    }
    }

        if (formatted.length > 3) {
        formatted = formatted.substring(0, 3);
    }
        input.value = formatted;
    }

        if (numberInput) {
        numberInput.addEventListener('input', function() {
        formatNumber(this);
    });

        numberInput.addEventListener('blur', function() {
        formatNumber(this);
    });

        numberInput.setAttribute('pattern', '[A-Z0-9]{1,6}');
        numberInput.setAttribute('title', 'Только латинские буквы (A,B,E,K,M,H,O,P,C,T,Y,X) и цифры');
        numberInput.setAttribute('placeholder', 'A123BC');
    }

        if (regionInput) {
        regionInput.addEventListener('input', function() {
        formatRegion(this);
    });

        regionInput.addEventListener('blur', function() {
        formatRegion(this);
    });

        regionInput.setAttribute('pattern', '\\d{1,3}');
        regionInput.setAttribute('title', 'Только цифры (2-3 цифры)');
        regionInput.setAttribute('placeholder', '777');
    }
    });
