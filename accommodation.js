let map;
let service;

// 지도 초기화
function initMap() {
    map = new google.maps.Map(document.createElement('div'), {
        center: {lat: 37.7749, lng: -122.4194},  // 기본 위치는 샌프란시스코
        zoom: 12
    });
    service = new google.maps.places.PlacesService(map);
}

// 호텔 검색 함수
function searchHotels() {
    const city = document.getElementById('search-bar').value;
    if (!city) {
        alert('도시명을 입력하세요!');
        return;
    }

    // Google Places API를 통해 도시명으로 장소 검색
    const request = {
        query: city + ' 호텔',
        fields: ['name', 'formatted_address', 'price_level', 'rating', 'photos'],
    };

    service.textSearch(request, function(results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            displayHotels(results);
        } else {
            alert('호텔 정보를 불러오지 못했습니다.');
        }
    });
}

// 호텔 정보 표시 함수
function displayHotels(hotels) {
    const hotelListContainer = document.getElementById('hotel-list');
    hotelListContainer.innerHTML = '';  // 기존의 호텔 리스트 초기화

    if (hotels.length === 0) {
        hotelListContainer.innerHTML = '<p>검색된 호텔이 없습니다.</p>';
        return;
    }

    hotels.forEach(hotel => {
        const hotelItem = document.createElement('div');
        hotelItem.classList.add('hotel-item');
        hotelItem.innerHTML = `
            <img src="${hotel.photos ? hotel.photos[0].getUrl() : ''}" alt="${hotel.name}">
            <h3>${hotel.name}</h3>
            <p><strong>주소:</strong> ${hotel.formatted_address}</p>
            <p><strong>가격 수준:</strong> ${getPriceLevel(hotel.price_level)}</p>
            <p><strong>평점:</strong> ${hotel.rating ? hotel.rating : '정보 없음'}</p>
        `;

        // 클릭 시 모달 창 열기
        hotelItem.onclick = function() {
            openHotelModal(hotel);
        };

        hotelListContainer.appendChild(hotelItem);
    });
}

// 가격 수준을 문자열로 변환하는 함수
function getPriceLevel(priceLevel) {
    switch (priceLevel) {
        case 0:
            return '매우 저렴';
        case 1:
            return '저렴';
        case 2:
            return '보통';
        case 3:
            return '비쌈';
        case 4:
            return '매우 비쌈';
        default:
            return '정보 없음';
    }
}

// 모달 창 열기
function openHotelModal(hotel) {
    document.getElementById('hotelName').textContent = hotel.name;
    document.getElementById('hotelImage').src = hotel.photos ? hotel.photos[0].getUrl() : '';
    document.getElementById('hotelAddress').textContent = '주소: ' + hotel.formatted_address;
    document.getElementById('hotelRating').textContent = '평점: ' + (hotel.rating || '정보 없음');
    document.getElementById('hotelDescription').textContent = '설명: 해당 호텔에 대한 추가 정보는 아직 없습니다.';

    // 모달 표시
    document.getElementById('hotelModal').style.display = 'block';
}

// 모달 닫기
document.querySelector('.close-btn').onclick = function() {
    document.getElementById('hotelModal').style.display = 'none';
};

// 배경 클릭 시 모달 닫기
window.onclick = function(event) {
    if (event.target === document.getElementById('hotelModal')) {
        document.getElementById('hotelModal').style.display = 'none';
    }
}

initMap();
