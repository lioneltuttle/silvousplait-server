import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:geolocator/geolocator.dart';
import 'package:latlong2/latlong.dart';

void main() {
  runApp(const SvpClientApp());
}

class SvpClientApp extends StatelessWidget {
  const SvpClientApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SVP Client',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const _SearchPage(),
    );
  }
}

class _SearchPage extends StatefulWidget {
  const _SearchPage();

  @override
  State<_SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<_SearchPage> {
  final TextEditingController _serviceController = TextEditingController();
  final TextEditingController _addressController = TextEditingController();
  final Dio _dio = Dio(
    BaseOptions(
      // Pour Android emulator, utiliser 10.0.2.2 au lieu de localhost.
      baseUrl: 'http://10.0.2.2:8080',
      connectTimeout: const Duration(seconds: 5),
      receiveTimeout: const Duration(seconds: 5),
    ),
  );

  final MapController _mapController = MapController();

  bool _loading = false;
  final List<String> _results = <String>[];
  double? _lat;
  double? _lon;

  static const double _defaultLat = 48.8566;
  static const double _defaultLon = 2.3522;

  @override
  void dispose() {
    _serviceController.dispose();
    _addressController.dispose();
    super.dispose();
  }

  Future<void> _useGps() async {
    var perm = await Geolocator.checkPermission();
    if (perm == LocationPermission.denied) {
      perm = await Geolocator.requestPermission();
    }
    if (perm == LocationPermission.deniedForever || perm == LocationPermission.denied) {
      if (!mounted) {
        return;
      }
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Permission de localisation refusée.')),
      );
      return;
    }
    final pos = await Geolocator.getCurrentPosition();
    setState(() {
      _lat = pos.latitude;
      _lon = pos.longitude;
    });
    _mapController.move(LatLng(pos.latitude, pos.longitude), 14);
    if (!mounted) {
      return;
    }
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Position GPS appliquée sur la carte.')),
    );
  }

  Future<void> _onLaunchSearch() async {
    final service = _serviceController.text.trim();
    if (service.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Merci de saisir un type de prestation')),
      );
      return;
    }

    final lat = _lat ?? _defaultLat;
    final lon = _lon ?? _defaultLon;

    setState(() {
      _loading = true;
      _results.clear();
    });

    try {
      final response = await _dio.post<Map<String, dynamic>>(
        '/api/demands',
        data: <String, dynamic>{
          'serviceType': service,
          'clientLatitude': lat,
          'clientLongitude': lon,
        },
      );

      final id = response.data?['id'];
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Demande envoyée (id=$id)')),
      );

      _simulateResults(service);
    } on DioException catch (e) {
      final status = e.response?.statusCode;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            status != null
                ? 'Erreur serveur ($status), merci de réessayer.'
                : 'Erreur réseau, merci de vérifier votre connexion.',
          ),
        ),
      );
    } catch (_) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Erreur inattendue, merci de réessayer.')),
      );
    } finally {
      if (mounted) {
        setState(() {
          _loading = false;
        });
      }
    }
  }

  Future<void> _simulateResults(String service) async {
    const artisans = <String>['Artisan proximité A', 'Artisan proximité B', 'Artisan proximité C'];
    for (final name in artisans) {
      await Future<void>.delayed(const Duration(seconds: 2));
      if (!mounted) {
        return;
      }
      setState(() {
        _results.add('$name — $service');
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final center = LatLng(_lat ?? _defaultLat, _lon ?? _defaultLon);

    return Scaffold(
      appBar: AppBar(
        title: const Text('SVP Client'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'Que puis-je faire pour vous ?',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _serviceController,
              decoration: const InputDecoration(
                labelText: 'Type de prestation (ex : Plombier, Fuite chauffe-eau)',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _addressController,
              decoration: const InputDecoration(
                labelText: 'Adresse (optionnel si GPS)',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 8),
            Align(
              alignment: Alignment.centerLeft,
              child: TextButton(
                onPressed: _loading ? null : _useGps,
                child: const Text('Utiliser ma position actuelle'),
              ),
            ),
            SizedBox(
              height: 180,
              child: ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: FlutterMap(
                  mapController: _mapController,
                  options: MapOptions(
                    initialCenter: center,
                    initialZoom: 13,
                  ),
                  children: [
                    TileLayer(
                      urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
                      userAgentPackageName: 'com.svp.client',
                    ),
                    MarkerLayer(
                      markers: [
                        Marker(
                          width: 40,
                          height: 40,
                          point: center,
                          child: const Icon(Icons.location_pin, color: Colors.red, size: 40),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 8),
            Expanded(
              child: ListView.builder(
                itemCount: _results.length,
                itemBuilder: (context, index) {
                  final item = _results[index];
                  return Card(
                    child: ListTile(
                      title: Text(item),
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 8),
            FilledButton(
              onPressed: _loading ? null : _onLaunchSearch,
              child: _loading
                  ? const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    )
                  : const Text('Lancer la recherche'),
            ),
          ],
        ),
      ),
    );
  }
}
