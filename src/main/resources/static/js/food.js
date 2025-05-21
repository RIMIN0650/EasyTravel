var map;
var infowindow;
var ps;
var markers = [];

const apiKey = 'fsq3lAlEzX9ysp9gHcT2SVCqX87Pz55FdyLcyCThLf980lU=';

function initMap() {
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567),
            level: 3
        };

    map = new kakao.maps.Map(mapContainer, mapOption);
    ps = new kakao.maps.services.Places();
    infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
}

function searchPlace() {
    var query = document.getElementById('search-input').value;
    if (!query) {
        alert("검색어를 입력해주세요.");
        return;
    }
    ps.keywordSearch(query, placesSearchCB);
}

function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        var bounds = new kakao.maps.LatLngBounds();
        clearMarkers();

        for (var i = 0; i < data.length; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        map.setBounds(bounds);
        fetchFoursquareData(data);
    }
}

function displayMarker(place) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x)
    });

    kakao.maps.event.addListener(marker, 'click', function () {
        infowindow.setContent('<div style="padding:5px; font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });

    markers.push(marker);
}

function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function fetchFoursquareData(places) {
    var restaurantList = document.getElementById('restaurant-list');
    restaurantList.innerHTML = "";

    places.forEach(function (place, index) {
        var query = place.place_name;
        var searchUrl = `https://api.foursquare.com/v3/places/search?query=${query}&limit=1&near=Seoul`;

        fetch(searchUrl, {
            headers: { 'Authorization': apiKey }
        })
        .then(response => response.json())
        .then(data => {
            var restaurant = data.results[0];
            if (restaurant) {
                var fsqId = restaurant.fsq_id;
                var detailUrl = `https://api.foursquare.com/v3/places/${fsqId}?fields=rating,photos,name,location`;

                return fetch(detailUrl, {
                    headers: { 'Authorization': apiKey }
                });
            }
        })
        .then(response => response ? response.json() : null)
        .then(detail => {
            if (!detail || !detail.rating) return; // ⭐ 별점 없는 경우 건너뜀

            var imageUrl = '/images/default-image.jpg';
            if (detail.photos && detail.photos[0]) {
                const photo = detail.photos[0];
                imageUrl = `${photo.prefix}500x500${photo.suffix}`;
            }

            const rating = detail.rating.toFixed(1);
            addRestaurantToList(detail, index, imageUrl, rating);
        })
        .catch(error => {
            console.error('Foursquare API 호출 실패:', error);
        });
    });
}

function addRestaurantToList(restaurant, index, imageUrl, rating) {
    var restaurantList = document.getElementById('restaurant-list');

    var div = document.createElement('div');
    div.classList.add('restaurant-item');
    div.innerHTML = `
        <div class="restaurant-image">
            <img src="${imageUrl}" alt="${restaurant.name}" />
        </div>
        <div class="info">
            <div class="info-text">
                <strong>${restaurant.name}</strong>
                <p>${restaurant.location.address || '주소 없음'}</p>
                <div class="rating">⭐ ${rating}</div>
                <button onclick="showOnMap(${index})">지도에서 보기</button>
            </div>
        </div>
    `;

    restaurantList.appendChild(div);
}


function showOnMap(index) {
    var position = markers[index].getPosition();
    map.panTo(position);
}

window.onload = initMap;
